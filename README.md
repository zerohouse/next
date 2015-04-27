# Next MVC + DAO Library
편합니다!

### Example

    @Controller
    @Mapping("/api/user")
	public class UserController {
		@Build
		DAO dao;
	
		@Build
		@ImplementedBy(DeleteRight.class)
		Right right;
		
		@Build("users.rootUser") // build.json내의 오브젝트
        User user;
	
		@Mapping(method = Method.GET)
		public User getState(@SessionAttribute("user") User user) {
			return user;
		}
	
		@Mapping(method = Method.POST)
		public Response register(@JsonParameter("user") User user, Http http) {
			if (!dao.insert(user))
				return new Json(true, "DB입력중 오류가 발생했습니다.", null);
			http.setSessionAttribute("user", user);
			return new Json(user);
		}
	
		@Mapping(value = "/registeredEmail", method = Method.GET)
		public boolean registeredCheck(@FromDB(keyParameter = "email") User user) {
			return true;
		}
	
		@Mapping(value = "/login", method = Method.POST)
		public Response login(@JsonParameter("user") User user, Http http) {
			User fromDB = dao.find(User.class, user.getEmail());
			if (fromDB == null)
				return new Json(true, "가입하지 않은 이메일입니다.", null);
			if (!fromDB.getPassword().equals(user.getPassword()))
				return new Json(true, "비밀번호가 다릅니다.", null);
			http.setSessionAttribute("user", fromDB);
			return new Json(fromDB);
		}
	
		@Mapping(value = "/logout", method = { Method.POST, Method.GET })
		public void logout(Http http) {
			http.removeSessionAttribute("user");
		}

	}


##GET
###Repository
	<repository>
		<id>next-mvn-repo</id>
		<url>https://raw.github.com/zerohouse/next/mvn-repo/</url>
		<snapshots>
			<enabled>true</enabled>
			<updatePolicy>always</updatePolicy>
		</snapshots>
	</repository>

###Dependency
	<dependency>
		<groupId>at.begin</groupId>
		<artifactId>next</artifactId>
		<version>0.1</version>
	</dependency>


#MVC

## 1. Class

### Http.class Interface
HttpImpl.class, HttpForTest.class

    public interface Http {
        String getParameter(String name);
        <T> T getJsonObject(Class<T> cLass, String name); // Json오브젝트를 꺼내옴(Gson기반)
        <T> T getJsonObject(Class<T> cLass); // Json오브젝트를 꺼내옴(Gson기반)
    	void forword(String path) throws ServletException, IOException;
    	void setContentType(String type);
    	void write(String string);
    	void addUriVariable(String uriVariable);
    	String getUriVariable(int number); // Uri에서 {}부분의 변수를 꺼내옴 example 참고
    	void setCharacterEncoding(String encording) throws UnsupportedEncodingException;
    	void sendNotFound();
    	void setSessionAttribute(String name, Object value);
    	void removeSessionAttribute(String name);
    	<T> T getSessionAttribute(Class<T> cLass, String name);
    	Object getSessionAttribute(String name);
    	void sendRedirect(String location);
    	void sendError(int errorNo);
    	void sendError(int errorNo, String errorMesage);
    	void setAttribute(String key, Object value);
    	Object getAttribute(String key);
    	HttpServletRequest getReq();
    	HttpServletResponse getResp();
    }

### Response.class : 출력할 형태
Json.class, Jsp.class, File.class
#### Construct
    new Json(JsonObject);
    new Jsp(Jsp파일명);
    new File(파일명);

## 2. Annotation

### Class Annotations
#### @Controller : 컨트롤러 클래스에 사용, @Mapping  : Url 매핑 정보를 정의,

### Method Annotations

#### @Mapping 클래스, 메서드 모두 사용
    String[] value() default ""; // 매핑될 url들
	String[] before() default ""; // 해당 메서드를 실행하기 전 실행될 메서드 
	String[] after() default ""; // 해당 메서드를 실행한 후 실행될 메서드
	String[] method() default "GET"; // 매핑될 메서드(Post, Get, Put, Delete등) 

    
#### @HttpMethod : 공통적으로 사용할 메서드 정의 @Mapping의 before, after에서 사용
    
    String value() default ""; // 매핑될 이름 값이 없으면 메서드 이름으로 매핑
    


### Parameter Annotations
#### @Parameter, @JsonParameter, @SessionAttribute, @FromDB(keyParameter="?")
    String value(); // 해당하는 속성키
	boolean require() default true; // True일때, 해당 속성이 없으면 에러를 Response함
        
#### example
    @Mapping(value = "/update", before = "loginCheck", method = Method.POST)
    public void updatePost(@Parameter("userId") String parameter, @FromDB(keyParameter="userId") User user2,
            @JsonParameter("Post") Post post, @SessionAttribute("user") User user) {
    }
        

# DAO
어노테이션 기반 모델 설정 -> JDBC 한줄로 해결
테이블 생성 SQL 파일도 필요없습니다.
    
## 1. Class

## DAO.class
	Map<String, Object> getRecord(String sql, Object... parameters);
	List<Map<String, Object>> getRecords(String sql, Object... parameters);
     List<Object> getRecordAsList(String sql, int resultSize, Object... parameters);
	List<List<Object>> getRecordsAsList(String sql, int resultSize, Object... parameters);
	<T> T find(String sql, Class<T> cLass, Object... parameters);
	<T> T find(Class<T> cLass, Object... parameters);
	<T> List<T> getList(String sql, Class<T> cLass, Object... parameters);
	<T> List<T> getList(Class<T> cLass);
	Boolean execute(String sql, Object... parameters);
	void close();
	boolean fill(Object record);
	boolean insert(Object record);
	boolean insertIfExistUpdate(Object record);
	boolean update(Object record);
	boolean delete(Object record);
    
### Example Usage
    DAO dao = new DAO();
    User user = dao.find(User.class, userId);
    List<User> users = dao.getList(User.class, "SELECT * FROM User Where User_Id=?", userId);
    dao.insert(user);
    dao.update(user);
    dao.delete(user);
    dao.insertIfExistUpdate(user);
    dao.fill(user); //키만있는 오브젝트 채우기
    dao.execute("DROP TABLE User");
    
## TableMaker.class, PackageCreator.class
아래의 어노테이션 설정하고 모델만 만들면 테이블 만들어줍니다.

### Example Usage
    PackageCreator.createTable(reset, "me.model"); // 해당 패키지 내의 테이블 생성
    TableMaker tm = new TableMaker(User.class, dao); // User 테이블 생성
    tm.dropTable();
	tm.createTable();
    tm.reset(); // 드롭 후 크리에이트
    
    
## 2. Annotation
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
    	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        
        @RequiredRegex(EMAIL_PATTERN)
        private String email;
        
        @OtherTable
        private List<Post> posts;
        
    }
    
    
## ### TestData 관리
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
    		users.add(user);
    		users.add(user);
    		users.add(user);
    		users.add(user);
    		this.user = user;
    	}
    }


##Logger 사용 
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
    	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
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
        "controllerPackage": "",
        "jspPath": ""
      },
      "database": {
        "modelPackage": "",
        "testDataPackage": "",
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
          "/api/*",
          "/user/*"
        ],
        "url": "localhost:8080",
        "controllerPackage": "me.controllers",
        "jspPath": "/WEB-INF/jsp/",
        "dateFormat" : "yyyy-MM-dd HH:mm:ss"
      },
      "database": {
        "modelPackage": "me.model",
        "testDataPackage": "me.model.test",
        "connectionSetting": {
          "jdbcUrl": "jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=utf8",
          "username": "root",
          "password": "",
          "maxConnectionsPerPartition": 30,
          "partitionCount": 2,
        },
        "createOption": {
          "resetTablesOnServerStart": true,
          "insertDataOnServerStart": true,
          "table_suffix": "ENGINE \u003d InnoDB DEFAULT CHARACTER SET utf8",
          "stringOptions": {
            "dataType": "TEXT",
         }
      }
    }


### Default Setting : 기본 세팅은 아래와 같습니다.
    {
      "mapping": {
        "mappings": [],
        "characterEncoding": "UTF-8",
        "url": "",
        "jspPath": ""
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
    
## build.json (resources/build.json)
### 빌드 example
    {
	  "User" : {
		"email" : "user1@gmail.com",
		"gender" : "m"
	  },
	  "JsonView" : {
	  	"view" : "view"
	  }
	}
	
### 빌드사용
	@Build("user")
	User user;
