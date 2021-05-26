package test.test1;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import test.test1.mybatis.pojo.DbPlayer;
import test.test1.mybatis.pojo.Player;

/**
 * Hello world!
 *
 */
public class App1 
{
    public static void main( String[] args )
    {
    	//mybatis的配置文件
        String resource = "mybatis_conf.xml";
        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = App1.class.getClassLoader().getResourceAsStream(resource);
        //构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
//        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is,"myown");
        
        //创建能执行映射文件中sql的sqlSession
        SqlSession session = sessionFactory.openSession();
        playerExample(session);
        
        session.close();
    }
    
    private static void playerExample(SqlSession session) {
    	
        String statement = "test.test1.mybatis.mapping.PlayerMapper.getAllPlayers";//映射sql的标识字符串
        //执行查询返回一个唯一user对象的sql
        List<Player> users = session.selectList(statement);
        System.out.println("Result by: " + statement);
        for(Player user : users) {
        	System.out.println(user);
        }
        statement = "test.test1.mybatis.mapping.PlayerMapper.getPlayerById";
        DbPlayer user = session.selectOne(statement, 36);
        System.out.println("getPlayerById:" + user);
    }
    
    
}
