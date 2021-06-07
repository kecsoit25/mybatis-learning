package test.test1;

import java.io.InputStream;
import java.util.ArrayList;
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
public class App2 
{
    public static void main( String[] args )
    {
    	//mybatis的配置文件
        String resource = "mybatis_conf.xml";
        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = App2.class.getClassLoader().getResourceAsStream(resource);
        //构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
//        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is,"myown");
        
        //创建能执行映射文件中sql的sqlSession
        SqlSession session = sessionFactory.openSession();
        playerSelectIfExample(session);
        playerSelectTrimWhereExample(session);
        playerSelectForeachExample(session);
//        playerInsertExample(session);
//        playerUpdateExample(session);
//        playerDeleteExample(session);
        session.close();
    }
    
    private static void playerSelectIfExample(SqlSession session) {
    	
        String statement = "test.test1.mybatis.mapping.PlayerMapper.getAllPlayersLikeNickname";//映射sql的标识字符串
        //执行查询返回一个唯一user对象的sql
        Player player = new Player();
        List<Player> users = session.selectList(statement, player);
        System.out.println("Result by: " + statement);
        for(Player user : users) {
        	System.out.println(user);
        }
        
        player.setNickname("bee");
        List<Player> users2 = session.selectList(statement, player);
        System.out.println("Result by: " + statement);
        for(Player user : users2) {
        	System.out.println(user);
        }
    }
    
    private static void playerSelectTrimWhereExample(SqlSession session) {
    	String statement = "test.test1.mybatis.mapping.PlayerMapper.getAllPlayersUseTrimWhere";//映射sql的标识字符串
        //执行查询返回一个唯一user对象的sql
        Player player = new Player();
        List<Player> users = session.selectList(statement, player);
        System.out.println("Result by: " + statement);
        for(Player user : users) {
        	System.out.println(user);
        }
        
        player.setNickname("bee");
        List<Player> users2 = session.selectList(statement, player);
        System.out.println("Result by: " + statement);
        for(Player user : users2) {
        	System.out.println(user);
        }
        
        player.setNickname(null);
        player.setId(49);
        List<Player> users3 = session.selectList(statement, player);
        System.out.println("Result by: " + statement);
        for(Player user : users3) {
        	System.out.println(user);
        }
        
        player.setId(null);
        player.setNickname("bee");
        player.setScore(45);
        List<Player> users4 = session.selectList(statement, player);
        System.out.println("Result by: " + statement);
        for(Player user : users4) {
        	System.out.println(user);
        }
    }
    
private static void playerSelectForeachExample(SqlSession session) {
    	
        String statement = "test.test1.mybatis.mapping.PlayerMapper.getPlayersByIds";//映射sql的标识字符串
        //执行查询返回一个唯一user对象的sql
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(48);
        ids.add(49);
        List<Player> users = session.selectList(statement, ids);
        System.out.println("Result by: " + statement);
        for(Player user : users) {
        	System.out.println(user);
        }
        
        
    }
    
}
