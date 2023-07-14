import spock.lang.Specification
import spock.lang.Subject
import org.springframework.dao.DataAccessException

class UserServiceSpec extends Specification {

    @Subject
    UserService userService = new UserService()

    def "getUserById should return the expected UserModel"() {
        given:
        def userId = "123"
        def expectedUser = new UserModel(id: userId, name: "Jose")
        def userDAOMock = Mock(UserDAO)
        userDAOMock.getUserById(_) >> expectedUser
        userService.userDAO = userDAOMock

        when:
        def result = userService.getUserById(userId)

        then:
        result == expectedUser
    }

    def "postUser should insert a new user and return the expected ResponseUser"() {
        given:
        def newUser = new UserModel(name: "Jose", email: "jose@example.com", password: "Password123")
        def responseUser = new ResponseUser(id: "456", message: "User created successfully")
        def userDAOMock = Mock(UserDAO)
        userDAOMock.insertUser(newUser) >> responseUser
        userService.userDAO = userDAOMock

        when:
        def result = userService.postUser(newUser)

        then:
        result == responseUser
    }

    def "postUser should throw an IllegalArgumentException for an invalid email format"() {
        given:
        def newUser = new UserModel(name: "Jose", email: "invalid_email", password: "Password123")

        when:
        userService.postUser(newUser)

        then:
        thrown(IllegalArgumentException)
    }

    def "postUser should throw a RuntimeException for a database error"() {
        given:
        def newUser = new UserModel(name: "Jose", email: "Jose@example.com", password: "Password123")
        def userDAOMock = Mock(UserDAO)
        userDAOMock.insertUser(_) >> { throw new DataAccessException("Database error") }
        userService.userDAO = userDAOMock

        when:
        userService.postUser(newUser)

        then:
        thrown(RuntimeException)
    }
}