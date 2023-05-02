package city.olooe.task;

import city.olooe.controller.UploadController;
import city.olooe.domain.AttachFileDTO;
import city.olooe.domain.AttachVO;
import city.olooe.mapper.AttachMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Log4j
public class FileCheckTask {
    @Autowired
    private AttachMapper attachMapper;
    // 0 0 2 * * 4#2, 4#4
    @Scheduled(cron = "0 0 4 * * *")
    public void check() {
        log.warn("file check");
    }

    public void checkFiles() {
        List<AttachVO> fileList = attachMapper.getOldFiles(); // oldFiles는 어제 파일들

        log.info("-------------------- DB 상 목록 --------------------");
        fileList.forEach(log::warn);
        log.info("총 개수" + fileList.size());

        log.info("-------------------- 어제 날짜 --------------------");
        log.info(getYesterday());

        log.info("-------------------- FS 목록 --------------------");
            List<File> files = fileList.stream().map(AttachFileDTO::getOriginFile).collect(Collectors.toList());
        fileList.stream().filter(vo -> vo.isImage()).forEach(vo -> files.add(vo.getThumbFile()));
        files.forEach(log::warn);

        log.info("-------------------- FS 폴더 내 모든 파일 목록 --------------------");
        List<File> realFiles = new ArrayList<>(Arrays.asList(new File(UploadController.getPATH(), getYesterday()).listFiles()));
        realFiles.forEach(log::info);

        log.info("-------------------- FS 폴더 내 모든 파일 목록 중 DB와 일치하지 않는 파일 목록 --------------------");
        realFiles = new ArrayList<>(Arrays.asList(new File(UploadController.getPATH(), getYesterday()).listFiles(f -> !files.contains(f))));
        realFiles.forEach(log::warn);
        realFiles.forEach(File::delete);
        realFiles.forEach(log::info);
    }

    private String getYesterday() {
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date().getTime() - 1000 * 60 * 60 * 24);
    }
}
