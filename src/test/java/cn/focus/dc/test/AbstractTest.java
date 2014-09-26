package cn.focus.dc.test;

import cn.focus.webjunit.MockRose;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(loader=RoseTestContextLoader.class)
public abstract class AbstractTest {
    
    protected static MockRose mockRose;
    
    private static Log logger = LogFactory.getLog(AbstractTest.class);
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        //init log4j
        org.apache.log4j.BasicConfigurator.configure();
        
        // rcarver - setup the jndi context and the datasource
        try {

            SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
            //focus_dc master
            BasicDataSource realDatasource = new BasicDataSource();
            realDatasource.setDriverClassName("org.gjt.mm.mysql.Driver");
            realDatasource.setUsername("develop");
            realDatasource.setPassword("p3m12d");
            realDatasource.setUrl("jdbc:mysql://10.10.90.156:3306/focus_dc");
            builder.bind("jdbc/focus_dc", realDatasource);
            builder.activate();
            
            //focus_dc slave
            realDatasource = new BasicDataSource();
            realDatasource.setDriverClassName("org.gjt.mm.mysql.Driver");
            realDatasource.setUsername("develop_read");
            realDatasource.setPassword("p3m12d");
            realDatasource.setUrl("jdbc:mysql://10.10.90.156:3306/focus_dc");
            builder.bind("jdbc/focus_dc_read", realDatasource);            
            builder.activate();
            
            //focud_xinfang_master
            realDatasource = new BasicDataSource();
            realDatasource.setDriverClassName("org.gjt.mm.mysql.Driver");
            realDatasource.setUsername("develop");
            realDatasource.setPassword("p3m12d");
            realDatasource.setUrl("jdbc:mysql://10.10.90.156:3306/app_xinfang");
            builder.bind("jdbc/xinfang", realDatasource);            
            builder.activate();
            
            //focud_xinfang_slave
            realDatasource = new BasicDataSource();
            realDatasource.setDriverClassName("org.gjt.mm.mysql.Driver");
            realDatasource.setUsername("develop_read");
            realDatasource.setPassword("p3m12d");
            realDatasource.setUrl("jdbc:mysql://10.10.90.156:3306/app_xinfang");
            builder.bind("jdbc/xinfang_read", realDatasource);            
            builder.activate();            

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }finally{
            mockRose = MockRose.getMockRose(); 
        }

    }

}
