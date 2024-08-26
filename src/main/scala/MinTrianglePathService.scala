import zio.stream.{ZPipeline, ZStream}
import zio.{ZIO, _}

import scala.math._

trait MinTrianglePathService {

  def findMinPath(path: String): Task[Option[Int]]

}

object MinTrianglePathService {

  class ServiceLive extends MinTrianglePathService {

    override def findMinPath(path: String): Task[Option[Int]] = {
      for {
        ref <- Ref.make[List[List[Int]]](List.empty)
        _ <- ZStream.fromFileName(path).via(ZPipeline.utf8Decode >>> ZPipeline.splitLines)
          .foreach { lines =>
            for {
              line <- ZIO.attempt(lines.split(" ").map(_.toInt).toList)
                .foldZIO(ex => ZIO.fail(new RuntimeException("Unable to parse and prepare data", ex)), x => ZIO.succeed(x))
              _ <- ref.modify(l => (ref, l :+ line))
            } yield ()
          }
        rawData <- ref.get
        validData <- validateData(rawData)
        result <- ZIO.attempt(minPath(validData))
      } yield result.headOption
    }

    private def validateData(list: List[List[Int]]): Task[List[List[Int]]] =
      if (list.isEmpty) ZIO.fail(new RuntimeException("Data is empty")) else ZIO.succeed(list)

    private def minPath(data: List[List[Int]]): List[Int] = {
      data.reduceRight { (firstLine, secondLine) =>
        firstLine.zip(secondLine.zip(secondLine.tail)).map {
          case (value, (left, right)) => min(left, right) + value
        }
      }
    }

  }

  lazy val live: ULayer[ServiceLive] = ZLayer.succeed(new ServiceLive)
}