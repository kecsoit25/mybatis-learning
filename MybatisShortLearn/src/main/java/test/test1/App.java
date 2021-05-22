package test.test1;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import test.test1.mybatis.pojo.MyObject;
import test.test1.mybatis.pojo.User;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	//mybatis的配置文件
        String resource = "mybatis_conf.xml";
        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = App.class.getClassLoader().getResourceAsStream(resource);
        //构建sqlSession的工厂
        //SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is,"myown");
        //使用MyBatis提供的Resources类加载mybatis的配置文件（它也加载关联的映射文件）
        //Reader reader = Resources.getResourceAsReader(resource); 
        //构建sqlSession的工厂
        //SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        //创建能执行映射文件中sql的sqlSession
        SqlSession session = sessionFactory.openSession();
        userExample(session);
        myobjectExample(session);
        session.close();
    }
    
    private static void userExample(SqlSession session) {
    	/**
         * 映射sql的标识字符串，
         * me.gacl.mapping.userMapper是userMapper.xml文件中mapper标签的namespace属性的值，
         * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
         */
        String statement = "test.test1.mybatis.mapping.userMapper.getAllUsers";//映射sql的标识字符串
        //执行查询返回一个唯一user对象的sql
        List<User> users = session.selectList(statement);
        for(User user : users) {
        	System.out.println(user);
        }
        statement = "test.test1.mybatis.mapping.userMapper.getUserById";
        User user = session.selectOne(statement, 1);
        System.out.println("getUserById:" + user);
    }
    
    private static void myobjectExample(SqlSession session) {
    	String st = "test.test1.mybatis.mapping.myobjectMapper.getAllObjects";
    	List<MyObject> objects = session.selectList(st);
    	for(MyObject obj : objects) {
    		System.out.println(obj);
    	}
    }
}
