package cn.focus.dc.focuswap.service;

import java.util.List;
import java.util.Set;

import cn.focus.dc.focuswap.dao.CMSManagerDAO;
import cn.focus.dc.focuswap.model.CMSManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CMSManagerService {

    @Autowired
    private CMSManagerDAO cmsManagerDao;


    public CMSManager getCMSManager(Integer uid) {        
        return cmsManagerDao.get(uid);
    }
    
    public List<CMSManager> getCMSManagerList (Set<Integer> idSet) {
        return cmsManagerDao.getList(idSet);
    }
}
