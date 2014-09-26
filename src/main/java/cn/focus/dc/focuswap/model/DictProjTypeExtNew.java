package cn.focus.dc.focuswap.model;

import java.util.List;

public class DictProjTypeExtNew extends DictProjTypeExt{

    /**
     * 
     */
    private static final long serialVersionUID = 8347507932855595645L;

    private Integer typeIdOld;
    
    private List<Integer> oldIds;

    public Integer getTypeIdOld() {
        return typeIdOld;
    }

    
    
    public void setTypeIdOld(Integer typeIdOld) {
        this.typeIdOld = typeIdOld;
    }

    public List<Integer> getOldIds() {
        return oldIds;
    }

    public void setOldIds(List<Integer> oldIds) {
        this.oldIds = oldIds;
    }

    

}
