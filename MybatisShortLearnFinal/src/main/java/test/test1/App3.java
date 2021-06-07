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
import test.test1.mybatis.pojo.PlayerWithQuestions;
import test.test1.mybatis.pojo.Question;

/**
 * Hello world!
 *
 */
public class App3 
{
    public static void main( String[] args )
    {
    	//mybatis的配置文件
        String resource = "mybatis_conf.xml";
        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = App3.class.getClassLoader().getResourceAsStream(resource);
        //构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
//        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is,"myown");
        
        //创建能执行映射文件中sql的sqlSession
        SqlSession session = sessionFactory.openSession();
        questionExample(session);
        questionPlayerExample(session);
        session.close();
    }
    
    private static void questionExample(SqlSession session) {
    	
        String statement = "test.test1.mybatis.mapping.QuestionMapper.selectAllQuestion";//映射sql的标识字符串
        //执行查询返回一个唯一user对象的sql
        
        List<Question> qs = session.selectList(statement);
        System.out.println("Result by: " + statement);
        for(Question q : qs) {
        	System.out.println(q);
        }
        
        
    }
    
    private static void questionPlayerExample(SqlSession session) {
    	
        String statement = "test.test1.mybatis.mapping.PlayerMapper.getAllPlayersWithQuestions";//映射sql的标识字符串
        //执行查询返回一个唯一user对象的sql
        
        List<PlayerWithQuestions> qs = session.selectList(statement);
        System.out.println("Result by: " + statement);
        for(PlayerWithQuestions q : qs) {
        	System.out.println(q);
        	for(Question qu : q.getQuestions()) {
        		System.out.print(qu);
        	}
        }
        
        
    }
    
    
}
