package test.test1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import test.test1.mybatis.pojo.Answer;
import test.test1.mybatis.pojo.Player;
import test.test1.mybatis.pojo.Question;

/**
 * Hello world!
 *
 */
public class App1 
{
	private static SqlSessionFactory sessionFactory;
	private static Player currentPlayer;
	private static List<String> commandList = new ArrayList<String>();
	private static String mapperPrefix = "test.test1.mybatis.mapping.";
	private static String playerMapper = "PlayerMapper.";
	private static String questionMapper = "QuestionMapper.";
	private static String answerMapper = "AnswerMapper.";
    public static void main( String[] args )
    {
    	//mybatis的配置文件
        String resource = "mybatis_conf.xml";
        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = App1.class.getClassLoader().getResourceAsStream(resource);
        //构建sqlSession的工厂
        sessionFactory = new SqlSessionFactoryBuilder().build(is);
//        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is,"myown");
        login();
        System.out.println("Hello " + currentPlayer);
        commandList.add("addPlayer");
    	commandList.add("listPlayer");
    	commandList.add("askQuestion");
    	commandList.add("listMyQuestion");
    	commandList.add("listOpenQuestion");
    	commandList.add("searchQuestion");
    	commandList.add("closeMyQuestion");
    	commandList.add("answer");
    	commandList.add("listAllAnswer");
    	commandList.add("bestAnswer");
    	commandList.add("exit");
        Scanner scan = new Scanner(System.in);
        while(true) {
        	listUsage();
        	System.out.println("Your Command:");
        	
        	String command = scan.nextLine();
        	if(command == null || !isCommandOK(command)) {
        		System.out.println("not supported command " + command);
        	} else {
        		try {
	        		if(command.startsWith("addPlayer")) {
	        			addPlayer(command);
	        		} else if(command.startsWith("listPlayer")) {
	        			listPlayer();
	        		} else if(command.startsWith("askQuestion")) {
	        			askQuestion(command);
	        		} else if(command.startsWith("listMyQuestion")) {
	        			listMyQuestion();
	        		} else if(command.startsWith("listOpenQuestion")) {
	        			ListOpenQuestion();
	        		} else if(command.startsWith("searchQuestion")) {
	        			searchQuestion(command);
	        			
	        		} else if(command.startsWith("closeMyQuestion")) {
	        			closeMyQuestion(command);
	        		} else if(command.startsWith("answer")) {
	        			answer(command);
	        		} else if(command.startsWith("listAllAnswer")) {
	        			listAllAnswer(command);
	        		} else if(command.startsWith("bestAnswer")) {
	        			bestAnswer(command);
	        		} else {
	        			System.out.println("Byebye");
	        			break;
	        		}
        		} catch(Exception e) {
        			System.err.println("执行" + command + "时报错");
        			System.err.println(e.getMessage());
        		}
        	}
        }
        scan.close();
        
    }
    
    
    private static void login() {
    	Scanner scan = new Scanner(System.in);
    	System.out.println("Login First");
    	System.out.print("Username:");
    	String username = scan.nextLine();
    	System.out.print("Password:");
    	String password = scan.nextLine();
    	//scan.close();
    	
    	String st = mapperPrefix + playerMapper + "getPlayerByUsername";
    	SqlSession session = sessionFactory.openSession();
    	Player player = session.selectOne(st, username);
    	session.close();
    	if(player == null || !player.getPassword().equals(password)) {
    		System.out.println("Login Fail");
    		System.exit(-1);
    	}
    	currentPlayer = player;
    	
    }
    
    private static void listUsage() {
    	System.out.println("You can do below:");
    	String str = "1. addPlayer [username] [password] [nickname]\n";
    	str += "2. listPlayer\n";
    	str += "3. askQuestion [credit] [value]\n";
    	str += "4. listMyQuestion\n";
    	str += "5. listOpenQuestion\n";
    	str += "6. searchQuestion [key]\n";
    	str += "7. closeMyQuestion [questionId]\n";
    	str += "8. answer [questionId] [answer]\n";
    	str += "9. listAllAnswer [questionId]\n";
    	str += "10. bestAnswer [questionId] [answerId]\n";
    	str += "11. exit\n";
    	System.out.print(str);
    	
    }
    
    private static boolean isCommandOK(String command) {
    	for(String c : commandList) {
    		if(command != null && command.startsWith(c)) {
    			return true;
    		}
    	}
    	return false;
    }

    private static void addPlayer(String command) {
    	String [] params = command.split(" ");
    	String errorSync = "格式不对";
    	String errorExist = "用户已经存在";
    	if(params.length != 4) {
    		System.out.println(errorSync);
    		return;
    	}
    	String username = params[1];
    	String password = params[2];
    	String nickname = params[3];
    	if(username == null || username.equals("") || password == null || password.equals("")
    			|| nickname == null || nickname.equals("")) {
    		System.out.println(errorSync);
    		return;
    	}
    	SqlSession session = sessionFactory.openSession();
    	String st = mapperPrefix + playerMapper + "getPlayerByUsername";
    	Player player = session.selectOne(st, username);
    	if(player != null) {
    		System.out.println(errorExist);
    		session.close();
    		return;
    	}
    	st = mapperPrefix + playerMapper + "insertPlayer";
    	player = new Player();
    	player.setName(nickname);
    	player.setPassword(password);
    	player.setScore(0);
    	player.setUsername(username);
    	session.insert(st, player);
    	session.commit();
    	session.close();
    	System.out.println("addPlayer done " + player);
    }
    
    private static void listPlayer() {
    	SqlSession session = sessionFactory.openSession();
    	String st = mapperPrefix + playerMapper + "getAllPlayers";
    	List<Player> players = session.selectList(st);
    	for(Player pl : players) {
    		System.out.println(pl);
    	}
    	session.close();
    }
    
    private static void askQuestion(String command) {
    	String[] params = command.split(" ");
    	String errorSync = "格式不对";
    	if(params.length < 3) {
    		System.out.println(errorSync);
    		return;
    	}
    	String credit = params[1];
    	try {
    		Integer.parseInt(credit);
    	} catch(Exception e) {
    		System.out.println(errorSync);
    		return;
    	}
    	//command.indexOf(params[0]);
    	command = command.substring(params[0].length()).trim();
    	String value = command.substring(params[1].length()).trim();
    	if(value == null || value.equals("")) {
    		System.out.println(errorSync);
    		return;
    	}
    	SqlSession session = sessionFactory.openSession();
    	String st = mapperPrefix + questionMapper + "insertQuestion";
    	Question q = new Question();
    	q.setCredit(Integer.parseInt(credit));
    	q.setIsOpen("Y");
    	q.setPlayer_id(currentPlayer.getId());
    	q.setValue(value);
    	session.insert(st, q);
    	session.commit();
    	session.close();
    	System.out.println("askQuestion done." + q);
    }
    
    private static void listMyQuestion() {
    	SqlSession session = sessionFactory.openSession();
    	String st = mapperPrefix + questionMapper + "selectQuestionsByPid";
    	List<Question> qs = session.selectList(st, currentPlayer.getId());
    	for(Question q : qs) {
    		System.out.println(q);
    	}
    	session.close();
    }
    
    private static void ListOpenQuestion() {
    	SqlSession session = sessionFactory.openSession();
    	String st = mapperPrefix + questionMapper + "selectOpenQuestions";
    	List<Question> qs = session.selectList(st);
    	for(Question q : qs) {
    		System.out.println(q);
    	}
    	session.close();
    }
    
    private static void searchQuestion(String command) {
    	String[] params = command.split(" ");
    	String errorSync = "格式不对";
    	if(params.length != 2) {
    		System.out.println(errorSync);
    		return;
    	}
    	String key = params[1];
    	SqlSession session = sessionFactory.openSession();
    	String st = mapperPrefix + questionMapper + "searchQuestions";
    	List<Question> qs = session.selectList(st, key);
    	for(Question q : qs) {
    		System.out.println(q);
    	}
    	session.close();
    }
    
    private static void closeMyQuestion(String command) {
    	String[] params = command.split(" ");
    	String errorSync = "格式不对";
    	if(params.length != 2) {
    		System.out.println(errorSync);
    		return;
    	}
    	String qid = params[1];
    	try {
    		Integer.parseInt(qid);
    	} catch(Exception e) {
    		System.out.println(errorSync);
    		return;
    	}
    	SqlSession session = sessionFactory.openSession();
    	String st = mapperPrefix + questionMapper + "selectQuestionById";
    	Question q = session.selectOne(st, Integer.parseInt(qid));
    	if(q == null) {
    		session.close();
    		System.out.println("问题不存在");
    		return;
    	}
    	if(q.getPlayer_id().intValue() != currentPlayer.getId().intValue()) {
    		session.close();
    		System.out.println("不能关闭别人的问题");
    		return;
    	}
    	if(q.getIsOpen().equals("N")) {
    		session.close();
    		System.out.println("之前已经关闭");
    		return;
    	}
    	st = mapperPrefix + questionMapper + "closeQuestion";
    	session.update(st, q.getId());
    	session.commit();
    	session.close();
    	System.out.println("问题已经关闭");
    }
    
    private static void answer(String command) {
    	String[] params = command.split(" ");
    	String errorSync = "格式不对";
    	if(params.length < 3) {
    		System.out.println(errorSync);
    		return;
    	}
    	String qId = params[1];
    	try {
    		Integer.parseInt(qId);
    	} catch(Exception e) {
    		System.out.println(errorSync);
    		return;
    	}
    	//command.indexOf(params[0]);
    	command = command.substring(params[0].length()).trim();
    	String value = command.substring(params[1].length()).trim();
    	if(value == null || value.equals("")) {
    		System.out.println(errorSync);
    		return;
    	}
    	SqlSession session = sessionFactory.openSession();
    	String st = mapperPrefix + questionMapper + "selectQuestionById";
    	Question q = session.selectOne(st, Integer.parseInt(qId));
    	if(q == null || q.getIsOpen().equals("N")) {
    		session.close();
    		System.out.println("无效的questionId");
    		return;
    	}
    	Answer an = new Answer();
    	an.setBest("N");
    	an.setPlayer_id(currentPlayer.getId());
    	an.setQuestion_id(q.getId());
    	an.setValue(value);
    	st = mapperPrefix + answerMapper + "submitAnswer";
    	session.insert(st, an);
    	session.commit();
    	session.close();
    	System.out.println("answer done " + an);
    }
    
    private static void listAllAnswer(String command) {
    	String[] params = command.split(" ");
    	String errorSync = "格式不对";
    	if(params.length != 2) {
    		System.out.println(errorSync);
    		return;
    	}
    	String qid = params[1];
    	try {
    		Integer.parseInt(qid);
    	} catch(Exception e) {
    		System.out.println(errorSync);
    		return;
    	}
    	SqlSession session = sessionFactory.openSession();
    	String st = mapperPrefix + questionMapper + "selectQuestionById";
    	Question q = session.selectOne(st, Integer.parseInt(qid));
    	if(q == null) {
    		session.close();
    		System.out.println("问题不存在");
    		return;
    	}
    	st = mapperPrefix + answerMapper + "selectAnswerByQid";
    	List<Answer> ans = session.selectList(st, q.getId());
    	for(Answer an: ans) {
    		System.out.println(an);
    	}
    	session.close();
    }
    
    private static void bestAnswer(String command) {
    	String[] params = command.split(" ");
    	String errorSync = "格式不对";
    	if(params.length != 3) {
    		System.out.println(errorSync);
    		return;
    	}
    	String qid = params[1];
    	try {
    		Integer.parseInt(qid);
    	} catch(Exception e) {
    		System.out.println(errorSync);
    		return;
    	}
    	String ansid = params[2];
    	try {
    		Integer.parseInt(ansid);
    	} catch(Exception e) {
    		System.out.println(errorSync);
    		return;
    	}
    	SqlSession session = sessionFactory.openSession();
    	String st = mapperPrefix + questionMapper + "selectQuestionById";
    	Question q = session.selectOne(st, Integer.parseInt(qid));
    	if(q == null) {
    		session.close();
    		System.out.println("问题不存在");
    		return;
    	}
    	st = mapperPrefix + answerMapper + "selectAnswerByQid";
    	List<Answer> ans = session.selectList(st, q.getId());
    	int answer = Integer.parseInt(ansid);
    	for(Answer an: ans) {
    		if(an.getId().intValue() == answer) {
    			if(an.getPlayer_id().intValue() == currentPlayer.getId().intValue()) {
    				session.close();
    				System.out.println("不能best的answer");
    				return;
    			} else {
	    			st = mapperPrefix + answerMapper + "bestAnswer";
	    			session.update(st, an.getId());
	    			st = mapperPrefix + playerMapper + "getPlayerById";
	    			Player player = session.selectOne(st, an.getPlayer_id());
	    			player.setScore(player.getScore().intValue() + q.getCredit().intValue());
	    			st = mapperPrefix + playerMapper + "updatePlayer";
	    			session.update(st, player);
	    			session.commit();
	    			session.close();
	    			System.out.println("done");
    				return;
    			}
    		}
    	}
    	System.out.println("没有这个answer id");
    	session.close();
    }
}
