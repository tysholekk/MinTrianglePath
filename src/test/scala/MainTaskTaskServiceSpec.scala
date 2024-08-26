import zio._
import zio.test.Assertion.{equalTo, fails}
import zio.test.{Assertion, TestEnvironment, ZIOSpecDefault, assert, assertTrue, _}

object MinTrianglePathServiceTest extends ZIOSpecDefault {

  override def spec: Spec[TestEnvironment with Scope, Any] = suite("Find triangle min path")(
    test("scenario 1. Empty data") {
      for {
        srv <- ZIO.service[MinTrianglePathService]
        result <- srv.findMinPath("src/test/resources/empty_data.txt").exit
      } yield assert(result)(fails(Assertion.hasMessage(equalTo("Data is empty"))))
    },
    test("scenario 2. Unparsable data") {
      for {
        srv <- ZIO.service[MinTrianglePathService]
        result <- srv.findMinPath("src/test/resources/unparsable_data.txt").exit
      } yield assert(result)(fails(Assertion.hasMessage(equalTo("Unable to parse and prepare data"))))
    },
    test("scenario 3. Provided task data") {
      for {
        srv <- ZIO.service[MinTrianglePathService]
        result <- srv.findMinPath("src/test/resources/provided_data.txt")
      } yield assertTrue(result.contains(18))
    },
    test("scenario 4. Provided task small data") {
      for {
        srv <- ZIO.service[MinTrianglePathService]
        result <- srv.findMinPath("src/test/resources/data_small.txt")
      } yield assertTrue(result.contains(50))
    },
    test("scenario 5. Provided task big data") {
      for {
        srv <- ZIO.service[MinTrianglePathService]
        result <- srv.findMinPath("src/test/resources/data_big.txt")
      } yield assertTrue(result.contains(2000))
    },
    test("scenario 6. Extra data case") {
      for {
        srv <- ZIO.service[MinTrianglePathService]
        result <- srv.findMinPath("src/test/resources/extra.txt")
      } yield assertTrue(result.contains(11))
    }

  ).provide(MinTrianglePathService.live)

}
