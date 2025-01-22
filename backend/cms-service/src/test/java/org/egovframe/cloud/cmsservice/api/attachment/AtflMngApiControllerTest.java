package org.egovframe.cloud.cmsservice.api.attachment;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Base64;
import org.egovframe.cloud.cmsservice.api.attachment.dto.*;
import org.egovframe.cloud.cmsservice.domain.attachment.AtflMng;
import org.egovframe.cloud.cmsservice.domain.attachment.AtflMngRepository;
import org.egovframe.cloud.cmsservice.service.attachment.AtflMngService;
import org.egovframe.cloud.cmsservice.utils.FileStorageUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
@ActiveProfiles(profiles = "test")
class AtflMngApiControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    FileStorageUtils fileStorageUtils;

    @Autowired
    AtflMngService atflMngService;

    @Autowired
    AtflMngRepository atflMngRepository;

    @AfterEach
    public void teardown() {
        List<AtflMng> all = atflMngRepository.findAll();
        for (AtflMng atflMng : all) {
            atflMngService.delete(atflMng.getAtflId());
        }
    }

    /**
     * file to byte[]
     *
     * @param file
     * @return
     */
    public byte[] getByteFile(File file) {
        byte[] data = new byte[(int) file.length()];
        try {
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(data, 0, data.length);
            inputStream.close();
        } catch (FileNotFoundException e) {
            log.debug("file not found = {}", e);
        } catch (IOException e) {
            log.debug("file IO exception = {}", e);
        }
        return data;
    }

    /**
     * test.txt 파일 생성
     *
     * @return
     * @throws IOException
     */
    public static Resource getTestFile() throws IOException {
        Path testFile = Files.createTempFile("test-file", ".txt");
        System.out.println("Creating and Uploading Test File: " + testFile);
        Files.write(testFile, "Hello World !!, This is a test file.".getBytes());
        testFile.toFile().deleteOnExit();
        return new FileSystemResource(testFile.toFile());
    }

    /**
     * 하나의 멀티파트 파일 생성
     *
     * @return
     * @throws IOException
     */
    private MultipartFile getMultipartFile() throws IOException {
        Resource resource = getTestFile();
        return new MockMultipartFile("files", resource.getFilename(),
                Files.probeContentType(resource.getFile().toPath()), resource.getInputStream());
    }

    /**
     * 여러 건의 멀티파트 파일 생성
     *
     * @param size
     * @return
     * @throws IOException
     */
    private List<MultipartFile> getMultipartFileList(int size) throws IOException {
        List<MultipartFile> multipartFiles = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            multipartFiles.add(getMultipartFile());
        }
        return multipartFiles;
    }

    /**
     * 여러 건의 .temp 파일 생성 후 AttachmentSaveRequestDto List return
     *
     * @param size
     * @return
     * @throws IOException
     */
    private List<AtflMngTempSaveRequestDto> getTempSaveDto(int size) throws IOException {
        List<MultipartFile> multipartFiles = getMultipartFileList(size);
        List<AtflMngFileResponseDto> responseDtos = atflMngService.uploadFiles(multipartFiles);

        List<AtflMngTempSaveRequestDto> saveRequestDtoList = new ArrayList<>();
        for (int i = 0; i < responseDtos.size(); i++) {
            AtflMngFileResponseDto responseDto = responseDtos.get(i);
            saveRequestDtoList.add(AtflMngTempSaveRequestDto.builder()
                    .physFileNm(responseDto.getPhysFileNm())
                    .orgnlFileNm(responseDto.getOrgnlFileNm())
                    .atflFlsz(responseDto.getSize())
                    .atflType(responseDto.getFileType())
                    .lnkgDmnNm("Policy")
                    .lnkgDmnId("testEntityId_"+i)
                    .build()
            );
        }
        return saveRequestDtoList;
    }

    @Test
    public void 이미지_BASE64인코딩후_업로드_정상() throws Exception {
        //given
        String url = "/api/v1/atfl/upload/editor";
        Resource testFile = getTestFile();

        String base64data = Base64.toBase64String(getByteFile(testFile.getFile()));
        AtflMngBase64RequestDto requestDto = AtflMngBase64RequestDto.builder()
                .fieldName("upload")
                .fileType("text")
                .fileBase64(base64data)
                .originalName(testFile.getFilename())
                .size(testFile.getFile().length())
                .build();

        //when
        ResponseEntity<AtflMngEditorResponseDto> responseEntity =
                restTemplate.postForEntity(url, requestDto, AtflMngEditorResponseDto.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getOriginalFileName()).isEqualTo(testFile.getFilename());
    }

    @Test
    public void 첨부파일_싱글_업로드_정상() throws Exception {
        //given
        String url = "/api/v1/atfl/upload";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", getTestFile());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        //when
        ResponseEntity<AtflMngFileResponseDto> responseEntity =
                restTemplate.postForEntity(url, requestEntity, AtflMngFileResponseDto.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void 첨부파일_멀티_업로드_정상() throws Exception {
        //given
        String url = "/api/v1/atfl/upload/multi";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("files", getTestFile());
        body.add("files", getTestFile());
        body.add("files", getTestFile());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        //when
        ResponseEntity<List<AtflMngFileResponseDto>> responseEntity =
                restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                        new ParameterizedTypeReference<List<AtflMngFileResponseDto>>() {});

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).hasSize(3);
    }

    @Test
    public void 에디터이미지업로드_후_이미지태그에서_이미지파일_조회_정상() throws Exception {
        //given
        Resource testFile = getTestFile();
        String base64data = Base64.toBase64String(getByteFile(testFile.getFile()));
        AtflMngBase64RequestDto requestDto = AtflMngBase64RequestDto.builder()
                .fieldName("upload")
                .fileType("text")
                .fileBase64(base64data)
                .originalName(testFile.getFilename())
                .size(testFile.contentLength())
                .build();
        AtflMngEditorResponseDto responseDto = atflMngService.uploadEditor(requestDto);

        String url = "/api/v1/atfl/images/editor/"+responseDto.getUrl();

        //when
        ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity(url, byte[].class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void 첨부파일코드로_목록조회() throws Exception {
        //given
        List<AtflMngTempSaveRequestDto> saveRequestDtoList = getTempSaveDto(2);
        String atflCd = atflMngService.save(saveRequestDtoList);

        String url = "/api/v1/atfl/attachments/"+atflCd;

        //when
        ResponseEntity<List<AtflMngResponseDto>> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<AtflMngResponseDto>>() {});

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).hasSize(2);
    }

    @Test
    public void 첨부파일_다운로드_정상() throws Exception {
        //given
        List<AtflMngTempSaveRequestDto> saveRequestDtoList = getTempSaveDto(1);
        String atflCd = atflMngService.save(saveRequestDtoList);
        List<AtflMngResponseDto> attachments = atflMngService.findByCode(atflCd);
        String atflId = attachments.get(0).getAtflId();

        String url = "/api/v1/atfl/download/"+atflId;

        //when
        ResponseEntity<Resource> responseEntity = restTemplate.getForEntity(url, Resource.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_OCTET_STREAM);
    }
}
