package variance

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/*
В проекторе для RedactedWordLine можно проецировать Slide[RedactedWordLine], но нельзя Slide[WordLine]
В проекторе для WordLine можно проецировать Slide[WordLine] и Slide[RedactedWordLine]
В проекторе для RedactedWordLine можно использовать Converter[RedactedWordLine] и Converter[WordLine]
В проекторе для WordLine можно использовать Converter[WordLine], но нельзя Converter[RedactedWordLine]
 */

class ProjectorSpec extends AnyFlatSpec with Matchers {
  "projector" should "for ReductedWordLine can project Slide[RedactedWordLine], but not Slide[WordLine]" in {
    val projector = new Projector[RedactedWordLine](new RedactedWordConverter(0.5))
    projector.project(
      HelloSlide[RedactedWordLine](
        Seq(new RedactedWordLine("Hello"), new RedactedWordLine("Kitty"))
      )
    )
    assertTypeError(
      "projector.project(" +
        "HelloSlide[WordLine](" +
        "Seq(" +
        "new WordLine(\"Hello\"), " +
        "new WordLine(\"Kitty\"))" +
        "))"
    )
  }

  "projector" should "for WordLine can project Slide[RedactedWordLine] and Slide[WordLine]" in {
    val projector = new Projector[WordLine](LineConverter)
    projector.project(
      HelloSlide[RedactedWordLine](
        Seq(new RedactedWordLine("Hello"), new RedactedWordLine("Kitty"))
      )
    )
    projector.project(
      HelloSlide[WordLine](
        Seq(new WordLine("Hello"), new WordLine("Kitty"))
      )
    )
  }

  "projector" should "for ReductedWordLine can use Slide[RedactedWordLine], and Slide[WordLine]" in {
    new Projector[RedactedWordLine](new RedactedWordConverter(0.5))
    new Projector[RedactedWordLine](LineConverter)
  }

  "projector" should "for WordLine can project Slide[WordLine], but not Slide[RedactedWordLine]" in {
    new Projector[WordLine](LineConverter)
    "new Projector[WordLine](new RedactedWordConverter(0.5))" shouldNot compile
  }
}
