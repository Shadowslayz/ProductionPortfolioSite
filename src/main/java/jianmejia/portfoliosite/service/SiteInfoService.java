package jianmejia.portfoliosite.service;

import jianmejia.portfoliosite.model.SiteInfo;
import jianmejia.portfoliosite.repository.SiteInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class SiteInfoService {
    private final SiteInfoRepository repo = new SiteInfoRepository();

    public SiteInfo get() {
        return repo.find();
    }

    public SiteInfo save(SiteInfo s) {
        return repo.save(s);
    }
}
