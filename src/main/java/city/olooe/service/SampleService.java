package city.olooe.service;

import city.olooe.mapper.SampleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Log4j
@Transactional
public class SampleService {
    private SampleMapper mapper;

    public void addData1(String data) {
        log.info("insert1()");
        mapper.insert1(data);
        log.info("insert2()");
        mapper.insert2(data);
        log.info("end");
    }

    @Transactional
    public void addData2(String data) {
        log.info("insert1()");
        mapper.insert1(data);
        log.info("insert2()");
        mapper.insert2(data);
        log.info("insert2()");
        mapper.insert2(data);
        log.info("insert2()");
        mapper.insert2(data);
        log.info("end");
    }
}
