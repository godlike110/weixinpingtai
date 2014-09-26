package cn.focus.dc.focuswap.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "xinfang")
public interface DaoGouDAO {

	//获取PC端的导购号+  status:-1 删除 1 编辑状态 2提交状态（待审核） 3审核不通过 4发布状态(审核通过)  5 下线
	@SQL("select pc_id from build_propose where id = :1 and status = 4")
	Integer getDaogouIdOfPc(Integer id);
}
