package variance

import scala.annotation.tailrec
import scala.util.Random

trait Converter[-S] {
  def convert(value: S): String
}

trait Slide[+R] {
  def read: (Option[R], Slide[R])
}

class Projector[R](converter: Converter[R]) {
  def project(screen: Slide[R]): String = {
    @tailrec
    def tailRecProject(screen: Slide[R], acc: String): String = screen.read match {
      case (None, _)            => acc
      case (Some(token), slide) => tailRecProject(slide, acc + converter.convert(token))
    }
    tailRecProject(screen, "")
  }
}

class WordLine(val word: String)

class RedactedWordLine(override val word: String) extends WordLine(word)

class RedactedWordConverter(redactionFactor: Double) extends Converter[RedactedWordLine] {
  override def convert(value: RedactedWordLine): String =
    if (new Random().nextDouble() > redactionFactor)
      "█" * value.word.length + "\n"
    else
      value.word + "\n"
}

object LineConverter extends Converter[WordLine] {
  override def convert(value: WordLine): String = value.word + "\n"
}

case class HelloSlide[R <: WordLine](lines: Seq[R]) extends Slide[R] {
  override def read: (Option[R], Slide[R]) = lines match {
    case Nil => (None, this)
    case _   => (Some(lines.head), HelloSlide(lines.tail))
  }
}
