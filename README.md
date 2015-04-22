# Next MVC + JDBC Library
편합니다!

# MVC Library

## 1. Class

### Http.class
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
Json.class, Jsp.class
#### Construct
    new Json(JsonObject);
    new Jsp(Jsp파일명);

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
    
#### @Before 메서드 실행전 실행될 메서드, @After 메서드 실행 후 실행될 메서드

### Example Controller

    @Controller
    @Mapping("/api")
    public class UserController {
        @Mapping(value = "/user/{}", before = "loginCheck", method = Method.GET)  
        public void getUser(Http http, DAO dao) { // 메서드 내에서 트랜젝션
            dao.getObject(User.class, http.getParameter("userId")); 
            String userId = http.getUriVariable(0); // {}의 변수 사용
        }
        
        @Mapping(value = "/update", before = "loginCheck", method = Method.POST)
    	public Post updatePost(@JsonParameter("Post") Post post, @SessionAttribute("user") User user) {
            if(!post.getUserId().equals(user.getId()))
                return new Json("권한이 없습니다");
    		return post; //Object Return시 Json으로 파싱되어 리턴됨.
        }
        
	    @HttpMethod("loginCheck")
	    public Result loginCheck(Http http) {
	        if(http.getSessionAttribute("user") == null)
	            return new Result("로그인이 필요한 서비스입니다.");
            return null;
	    }
        
        @Before // 이 컨트롤러 뿐만 아니라 모든 컨트롤러에 적용
        public Result before(Http http) {
	        if(http.getSessionAttribute("user") == null)
	            return new Result("로그인이 필요한 서비스입니다.");
            return null;
	    }
    }
    


### Field Annotations
#### @DateFormat // 필드에 사용, Gson변환시 지정된 데이트포맷 사용
    

### Parameter Annotations
#### @Parameter, @JsonParameter, @SessionAttribute, @FromDB(keyParameter="?")
    String value(); // 해당하는 속성키
	boolean require() default true; // True일때, 해당 속성이 없으면 에러를 Response함
        
#### example
    @Mapping(value = "/update", before = "loginCheck", method = Method.POST)
    public void updatePost(@Parameter("userId") String parameter, @FromDB(keyParameter="userId") User user2,
            @JsonParameter("Post") Post post, @SessionAttribute("user") User user) {
    }
        

# JDBC Library
어노테이션 기반 모델 설정 -> JDBC 한줄로 해결
테이블 생성 SQL 파일도 필요없습니다.
    
## 1. Class

## DAO.class
    List<Object> getRecord(String sql, int resultSize, Object... parameters);
	Map<String, Object> getRecordMap(String sql, Object... parameters);
	List<List<Object>> getRecords(String sql, int resultSize, Object... parameters);
	List<Map<String, Object>> getRecordMaps(String sql, Object... parameters);
	<T> T getObject(String sql, Class<T> cLass, Object... parameters);
	<T> T getObject(Class<T> cLass, Object... parameters);
	<T> List<T> getObjects(String sql, Class<T> cLass, Object... parameters);
	<T> List<T> getObjects(Class<T> cLass);
	Boolean execute(String sql, Object... parameters);
	void close();
	boolean fill(Object record);
	boolean insert(Object record);
	boolean insertIfExistUpdate(Object record);
	boolean update(Object record);
	boolean delete(Object record);
	BigInteger getLastKey();
    
### Example Usage
    DAO dao = new Mysql();
    User user = dao.getObject(User.class, userId);
    List<User> users = dao.getObjects(User.class, "SELECT * FROM User Where User_Id=?", userId);
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



# Setting (resource/nextSetting.json)
web.xml ...등 기타 세팅파일 필요없습니다. 다지우시고 이거만. 리소스 아래에.

    {
		"mapping" : {
			"mappings" : ["/api/*", "/user/*"],
			"characterEncoding" : "UTF-8",
			"url":"localhost:8080",
			"controllerPackage" : "me.controllers",
			"jspPath" : "/WEB-INF/jsp/"
		},
		"logger" : {
			"level" : "ALL",
			"logFilePath" : "/log/",
			"pattern" : "%level [%thread] %msg - %logger{10} : %file:%line %date%n"
		},
		"database" : {
	        "modelPackage" : "me.model",
	        "testDataPackage" : "me.model.test",
	        "connectionSetting" : {
				"jdbcUrl" : "jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=utf8",
	        	"username" : "root",
	        	"password" : "",
	        	"minConnectionsPerPartition" : 5,
				"maxConnectionsPerPartition" : 10,
				"setPartitionCount" : 1
	        	},
			"createOption" : {
			    "createTablesOnServerStart" : true,
	        	"resetTablesOnServerStart" : false,
	        	"insertDataOnServerStart" : true,
				"table_suffix" : "ENGINE = InnoDB DEFAULT CHARACTER SET utf8",
				"stringOptions" : {
					"dataType" : "VARCHAR(255)",
					"notNull" : true,
					"hasDefaultValue" : true,
					"defaultValue" : ""
				},
				"integerOptions" : {
					"dataType" : "INTEGER",
					"notNull" : true,
					"hasDefaultValue" : true,
					"defaultValue" : "0"
				},
				"booleanOptions" : {
					"dataType" : "TINYINT(1)",
					"notNull" : true,
					"hasDefaultValue" : true,
					"defaultValue" : "0"
				},
				"dateOptions" : {
					"dataType" : "DATETIME",
					"notNull" : true,
					"hasDefaultValue" : true,
					"defaultValue" : "CURRENT_TIMESTAMP"
				},
				"floatOptions" : {
					"dataType" : "FLOAT",
					"notNull" : true,
					"hasDefaultValue" : true,
					"defaultValue" : "0"
				},
				"longOptions" : {
					"dataType" : "BIGINT",
					"notNull" : true,
					"hasDefaultValue" : true,
					"defaultValue" : "0"
				}
			}
		}
	}