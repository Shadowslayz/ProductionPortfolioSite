package jianmejia.portfoliosite.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import jianmejia.portfoliosite.model.SiteInfo;

import java.io.File;
import java.io.IOException;

public class SiteInfoRepository {
    private static final String FILE_PATH = "/opt/portfolio/data/siteinfo.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SiteInfo find() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return new SiteInfo();
            }
            return objectMapper.readValue(file, SiteInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new SiteInfo();
        }
    }

    public SiteInfo save(SiteInfo siteInfo) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), siteInfo);
            return siteInfo;
        } catch (IOException e) {
            e.printStackTrace();
            return siteInfo;
        }
    }
}
