import zio.test._
import zio.{Scope, ZIO}

object MinTrianglePathTest extends ZIOSpecDefault {

  override def spec: Spec[TestEnvironment with Scope, Any] = suite("Find triangle min path")(
    test("scenario 1. Empty data") {
      for {
        srv <- ZIO.service[MinTrianglePath]
        result <- srv.findMinPath("src/test/resources/empty_data.txt")
      } yield assertTrue(result.contains(0))
    },
    test("scenario 2. Unparsable data") {
      for {
        srv <- ZIO.service[MinTrianglePath]
        result <- srv.findMinPath("src/test/resources/unparsable_data.txt")
      } yield assertTrue(result.contains(0))
    },
    test("scenario 3. Provided task data") {
      for {
        srv <- ZIO.service[MinTrianglePath]
        result <- srv.findMinPath("src/test/resources/provided_data.txt")
      } yield assertTrue(result.contains(18))
    },
    test("scenario 4. Provided task small data") {
      for {
        srv <- ZIO.service[MinTrianglePath]
        result <- srv.findMinPath("src/test/resources/data_small.txt")
      } yield assertTrue(result.contains(50))
    },
    test("scenario 5. Provided task big data") {
      for {
        srv <- ZIO.service[MinTrianglePath]
        result <- srv.findMinPath("src/test/resources/data_big.txt")
      } yield assertTrue(result.contains(2000))
    }

  ).provide(MinTrianglePath.live)

}
