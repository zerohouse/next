# Next MVC + DAO Library 0.1.2.1
편합니다!


##GET
pom.xml에 아래의 레파지토리와 Dependency설정을 추가합니다.

###Repository
    <repository>
        <id>next-mvn-repo</id>
		<url>https://raw.github.com/zerohouse/next/mvn-repo/</url>
	</repository>

###Dependency
	<dependency>
		<groupId>at.begin</groupId>
		<artifactId>next</artifactId>
		<version>0.1.2.1</version>
	</dependency>


#MVC

## Example Usage

    @Controller
    @Mapping(value="/api/user", before="loginCheck")
	public class UserController {
		@Build
		GDAO<User> userDAO;
	
		@Build
		@ImplementedBy(DeleteRight.class)
		Right right;
		
		@Mapping(value = "/login", method = Method.POST, before="!loginCheck")
		public Response login(@JsonParameter("user") User user, HttpSession session) {
			User fromDB = userDao.find(user.getEmail());
			if (fromDB == null)
				return new Json(true, "가입하지 않은 이메일입니다.", null);
			if (!fromDB.getPassword().equals(user.getPassword()))
				return new Json(true, "비밀번호가 다릅니다.", null);
			session.setAttribute("user", fromDB);
			return new Json(fromDB);
		}
	}

## Method Return Type별 응답
#### 1. Response.class Interface

    new Json(JsonObject);
    new Jsp(Jsp파일명);
    new StaticFile(파일명); // webapp/파일
    
#### 2-1. String이 forward:로 시작할때 :뒤의 Path로 forward(클라이언트에서 주소 바뀌지 않음)
#### 2-2. String이 redirect:로 시작할때, :뒤의 Path로 reidrect(클라이언트 주소 바뀜) 
#### 2-3. String이 error:(no):(message)로 시작할때, :뒤의 메시지로 에러를 리턴
	ex)
	return "forward:/index.html" // forward to : /index.html 
	return "redirect:/index.html" // redirect to : /index.html 
	return "error:404" // 404에러 리턴
	return "error:404:페이지가 없습니다." // 404에러 리턴 + 에러메시지 리턴
#### 4. Object Return시 new Json(Object)로 간주 JSON으로 응답함.
	ex)
	return "/index.html" // JsonObject Return {"response":"/index.html"}
#### 5. 리턴값 없으면 empty JSON 오브젝트 리턴



### Annotations
#### @Controller [클래스 레벨]
컨트롤러 클래스에 사용

#### @Mapping [클래스, 메서드 레벨]
Url 매핑 정보를 정의

    String[] value() default ""; // 매핑될 url들
	String[] before() default ""; // 해당 메서드를 실행하기 전 실행될 메서드 
	String[] after() default ""; // 해당 메서드를 실행한 후 실행될 메서드
	String[] method() default "GET"; // 매핑될 메서드(Post, Get, Put, Delete등) 
	
### Uri 변수의 사용
	모든 파라미터를 받을때 {}와 *를 사용합니다.
	@Mapping("/{variableName}/*");
     *와 {}의 차이점은 변수를 꺼낼 수 있느냐의 여부입니다.
	http.getUriVariable("variableName");
	혹은 파라미터에 @UriVariable("variableName") String uri로 사용가능합니다.

#### @HttpMethods [클래스 레벨]
@HttpMethod메서드 클래스에 선언.

#### @HttpMethod [메서드 레벨]
공통적으로 사용할 메서드 정의 @Mapping의 before, after에서 사용

    String value() default ""; // 매핑될 이름 값이 없으면 메서드 이름으로 매핑
    
#### @Parameter, @JsonParameter, @SessionAttribute, @DB(keyParameter="?"), @Stored [파라미터 레벨]

#### example
    @Mapping(value = "/update", before = "loginCheck", method = Method.POST)
    public void updatePost(@Parameter("userId") String parameter,
     		@FromDB(keyParameter="userId") User user2,
              @JsonParameter("Post") Post post,
              @SessionAttribute("user") User user, @Stored List<String> mylist) {
              //Stored의 경우
              //Store store를 꺼내 저장한 속성을 뺄 수 있음.
    }
    
### Http.class Interface
HttpImpl.class, HttpForTest.class
HttpSevlet req와 resp의 Wrapper 클래스, 익셉션제거

#### example
    @Mapping(method = Method.GET, before="loginCheck")
    public void getAnswers(Http http) {
        http.forward("/index")
	}
    
#### vs 직접 사용 하는 경우
    @Mapping(method = Method.GET, before="loginCheck")
    public void getAnswers(HttpServletRequest req, HttpServletResponse res) {
        RequestDispatcher rd = req.getRequestDispatcher(path);
    	try {
			rd.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


        
##Build를 통한 Dependency Injection (resources/build.json)
1. Javascript Object사용
2. '#'으로 아이디 지정



### Example
#### 1. build.json
    {
      "right" : {
           "ALLMighty" : "#Users.rootUser", // 다른 오브젝트를 참조시
                                                #으로 아이디 지정
           "newBoard" : "#Users.operators"
      },
      "Users" {
       	  "rootUser" : {
	        	       "email" : "user1@gmail.com",
		               "gender" : "m"
	  	            	}
	       },
           "operators" : [
                "goo@ggo.com" ,
                "goo3@ggo.com" ,
                "goo4@ggo.com" ,
                "goo5@ggo.com" ,
           ]
	}
	
#### 2. build
	@Build("Users.rootUser")  // 자바스크립트처럼 셀렉트
	private User user;
    
    @Build("right")
    @ImplementedBy(AllRight.class) // 인터페이스일 경우 구현체
    private Right right;

# DAO
어노테이션 기반 모델 설정 -> JDBC 한줄로 해결
테이블 생성 SQL 파일도 필요없습니다.
    

## DAO.class, GDAO<T>.class
    
### Example Usage
    DAO dao = new DAO();
    User user = dao.find(User.class, userId);
    
    GDAO<User> userDao = new GDAO<User>(User.class);
    User user2 = userDAO.find(userId);
    
    user.equals(user2); // true
    
### Transaction : 사용후 반드시 DAO를 close해야함.
메서드 파라미터에서 사용시, 메서드 레벨 실행 후 close()호출함
 
    DAO dao = new DAO(new Transaction());
    dao.close();
    GDAO<User> gdao = new GDAO<User>(new Transaction());
    gdao.close();
    
#### example
    @Mapping(method = Method.GET, before="loginCheck")
    public void getAnswers(DAO dao, GDAO<User> userDAO) {
        // 파라미터에서 사용시 메서드 내에서 트랜젝션
        dao.insert(user);
        dao.update(user);
        userDAO.delete(user);
    } // 이후에 close()호출되므로 close()하지 않아도 됨.
    
## TableMaker.class
아래의 어노테이션 설정하고 모델만 만들면 테이블 만들어줍니다.

### Example Usage
    boolean ifExistDrop = false;
    TableMaker.create(ifExsitDrop); // 모든 테이블 생성
    TableMaker tm = new TableMaker(User.class, dao); // User 테이블 생성
    tm.dropTable();
	tm.createTable();
    tm.reset(); // 드롭 후 크리에이트
    
    
### @Table, @Key, @Column, @Exclude, @RequiredRegex

### Example Model
    @Table
    public class User {
        @Key(AUTO_INCREMENT = true)
    	private Integer id;
    	@Column(DATA_TYPE="TEXT", function="INDEX")
    	private String introduce;
    	private Integer age;
        
        @Exclude
    	private static final String EMAIL_PATTERN =
    	"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+
    		"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        
        @RequiredRegex(EMAIL_PATTERN)
        private String email;
        
        @OtherTable
        private List<Post> posts;
        
    }
    
    
### TestData 관리
    @TestData
    public class Tests {
        @InsertList
	    	private List<User> users;
	    	
	    	@Insert
	    	private User user;
	    
	    	public Tests() {
	    		users = new ArrayList<User>();
	    		User user = new User(null, "ab", 1);
	    		users.add(user);
	    		this.user = user;
	    	}
    }


### Logger 사용 
    LoggerUtil.getLogger(Class<?> type); 
    private static final Logger logger = LoggerUtil.getLogger(Mapper.class);


# Setting
1. 아래 web.xml을 webapp디렉토리의 WEB-INF폴더 내에 위치.
2. resource폴더 내에 next.json 위치 (기본 세팅을 담당)
3. resource폴더 내에 build.json 위치 (빌드할 오브젝트 JsonFormat)

## web.xml (webapp/WEB-INF/web.xml)
    <?xml version="1.0" encoding="UTF-8"?>
    <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://java.sun.com/xml/ns/javaee"
    	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
    	http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
    	version="3.0">
    	<listener>
    		<listener-class>next.Next</listener-class>
    	</listener>
    </web-app>


## next.json (resources/next.json)
### Setting Required Options : 아래 옵션은 필수입니다.

    {
      "mapping": {
        "mappings": [],
        "url": "",
        "jspPath": ""
      },
      "database": {
        "connectionSetting": {
          "jdbcUrl": "",
          "username": "",
          "password": ""
        }
      }
    }


### Setting Example : 필요한 옵션을 더 세팅하여 세팅 파일을 만듭니다.

    {
      "mapping": {
        "mappings": [
          "/",
          "!*.html",
          "!/css/*"
        ],
        "url": "localhost:8080",
        "jspPath": "/WEB-INF/jsp/",
        "dateFormat" : "yyyy-MM-dd HH:mm:ss"
      },
      "database": {
        "connectionSetting": {
          "jdbcUrl": "jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=utf8",
          "username": "root",
          "password": "",
          "maxConnectionsPerPartition": 30,
          "partitionCount": 2
        },
        "createOption": {
          "resetTablesOnServerStart": true,
          "insertDataOnServerStart": true,
          "table_suffix": "ENGINE \u003d InnoDB DEFAULT CHARACTER SET utf8",
          "stringOptions": {
            "dataType": "TEXT"
         }
      }
    }


### Default Setting : 기본 세팅은 아래와 같습니다.
    {
	  "mapping": {
	    "mappings": [],
	    "characterEncoding": "UTF-8",
	    "url": "",
	    "jspPath": "",
	    "dateFormat" : "yyyy-MM-dd HH:mm:ss",
	    "upload" : {
		  "location" : "uploads/",
		  "tempSaveLocation" : "uploads/temp/",
		  "needDirectories" : [],
		  "maxFileSize" : 1024*1024*5,
		  "maxRequestSize" : 1024*1024*5*5,
		  "fileSizeThreshold" : 1024*1024
	    }
	  },
	  "logger": {
	    "level": "ALL",
	    "logFilePath": "/log/",
	    "pattern": "%level [%thread] %msg - %logger{10} : %file:%line %date%n"
	  },
	  "database": {
	    "connectionSetting": {
	      "minConnectionsPerPartition": 0,
	      "maxConnectionsPerPartition": 10,
	      "acquireIncrement": 2,
	      "partitionCount": 1,
	      "jdbcUrl": "",
	      "username": "",
	      "password": "",
	      "idleConnectionTestPeriodInSeconds": 14400,
	      "idleMaxAgeInSeconds": 3600,
	      "statementsCacheSize": 0,
	      "statementsCachedPerConnection": 0,
	      "releaseHelperThreads": 0,
	      "statementReleaseHelperThreads": 0,
	      "closeConnectionWatch": false,
	      "logStatementsEnabled": false,
	      "acquireRetryDelayInMs": 7000,
	      "acquireRetryAttempts": 5,
	      "lazyInit": false,
	      "transactionRecoveryEnabled": false,
	      "disableJMX": false,
	      "queryExecuteTimeLimitInMs": 0,
	      "poolAvailabilityThreshold": 0,
	      "disableConnectionTracking": false,
	      "connectionTimeoutInMs": 0,
	      "closeConnectionWatchTimeoutInMs": 0,
	      "maxConnectionAgeInSeconds": 0,
	      "serviceOrder": "FIFO",
	      "statisticsEnabled": false,
	      "defaultAutoCommit": true,
	      "defaultReadOnly": false,
	      "defaultTransactionIsolationValue": -1,
	      "externalAuth": false,
	      "deregisterDriverOnClose": false,
	      "nullOnConnectionTimeout": false,
	      "resetConnectionOnClose": false,
	      "detectUnresolvedTransactions": false,
	      "poolStrategy": "DEFAULT",
	      "closeOpenStatements": false,
	      "detectUnclosedStatements": false
	    },
	    "createOption": {
	      "createTablesOnServerStart": true,
	      "resetTablesOnServerStart": false,
	      "insertDataOnServerStart": false,
	      "table_suffix": "ENGINE \u003d InnoDB DEFAULT CHARACTER SET utf8",
	      "stringOptions": {
	        "dataType": "VARCHAR(255)",
	        "notNull": true,
	        "hasDefaultValue": true,
	        "defaultValue": ""
	      },
	      "integerOptions": {
	        "dataType": "INTEGER",
	        "notNull": true,
	        "hasDefaultValue": true,
	        "defaultValue": 0
	      },
	      "booleanOptions": {
	        "dataType": "TINYINT(1)",
	        "notNull": true,
	        "hasDefaultValue": true,
	        "defaultValue": 0
	      },
	      "dateOptions": {
	        "dataType": "DATETIME",
	        "notNull": true,
	        "hasDefaultValue": true,
	        "defaultValue": "CURRENT_TIMESTAMP"
	      },
	      "floatOptions": {
	        "dataType": "FLOAT",
	        "notNull": true,
	        "hasDefaultValue": true,
	        "defaultValue": 0
	      },
	      "longOptions": {
	        "dataType": "BIGINT",
	        "notNull": true,
	        "hasDefaultValue": true,
	        "defaultValue": 0
	      }
	    }
	  }
	}

    
