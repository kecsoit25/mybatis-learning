package test.test1;

import java.io.InputStream;
import java.util.Calendar;
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
        playerSelectExample(session);
        playerInsertExample(session);
        playerUpdateExample(session);
        playerDeleteExample(session);
        session.close();
    }
    
    private static void playerSelectExample(SqlSession session) {
    	
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
        
        statement = "test.test1.mybatis.mapping.PlayerMapper.getPlayerByUsername";
        DbPlayer user1 = session.selectOne(statement, "qingke");
        System.out.println("getPlayerByUsername:" + user1);
        
        
    }
    
    private static void playerInsertExample(SqlSession session) {
    	Player player = new Player();
        player.setNickname("bee");
        player.setPassword("password123456");
        player.setScore(50);
        player.setUsername("beeplayer" + Calendar.getInstance().getTimeInMillis());//duplicate username error
        System.out.println(player);
        String statement = "test.test1.mybatis.mapping.PlayerMapper.insertPlayer";
        int result = session.insert(statement, player);
        session.commit();
        System.out.println("update row=" + result + "player:" + player);
    }
    
    private static void playerUpdateExample(SqlSession session) {
    	String statement = "test.test1.mybatis.mapping.PlayerMapper.getPlayerByUsernameOK";
    	Player player = session.selectOne(statement, "beeplayer");
    	System.out.println(player);
        player.setNickname("beeUpdate");
        player.setPassword("1qaz@WSX");
        player.setScore(40);
        //player.setUsername("beeplayer");//duplicate username error
        //System.out.println(player);
        statement = "test.test1.mybatis.mapping.PlayerMapper.updatePlayer";
        int result = session.update(statement, player);
        session.commit();
        System.out.println("update row=" + result + "player:" + player);
    }
    
    private static void playerDeleteExample(SqlSession session) {
    	String statement = "test.test1.mybatis.mapping.PlayerMapper.deletePlayerById";
    	int result = session.delete(statement, 45); //select a id first
    	System.out.println("delete row=" + result);
    	session.commit();
    	statement = "test.test1.mybatis.mapping.PlayerMapper.getAllPlayers";//映射sql的标识字符串
        //执行查询返回一个唯一user对象的sql
        List<Player> users = session.selectList(statement);
        System.out.println("Result by: " + statement);
        for(Player user : users) {
        	System.out.println(user);
        }
    }
    
}
