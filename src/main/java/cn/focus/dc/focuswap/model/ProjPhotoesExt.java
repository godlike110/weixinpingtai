package cn.focus.dc.focuswap.model;

import cn.focus.dc.building.model.ProjPhotoes;

public class ProjPhotoesExt extends ProjPhotoes implements PhotoRealPathGenerable{

    private static final long serialVersionUID = 5911607494589524013L;
    
    private String realPath;

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }
    

}
