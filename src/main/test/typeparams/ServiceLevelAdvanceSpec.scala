package typeparams;

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ServiceLevelAdvanceSpec extends AnyFlatSpec with Matchers {
  "advance" should "return new ServiceLevelAdvance with specified type" in {
    val economyServiceLevel = new ServiceLevelAdvance[Economy]
    val special1b = economyServiceLevel.advance[Special1b]
    val extendedEconomy = economyServiceLevel.advance[ExtendedEconomy]
    val business = economyServiceLevel.advance[Business]
    val elite = economyServiceLevel.advance[Elite]
    val platinum = economyServiceLevel.advance[Platinum]
  }

  "advance" should "can not increase service level beyond its branch" in {
    val updatedEconomyServiceLevel = new ServiceLevelAdvance[UpgradedEconomy]
    "updatedEconomyServiceLevel.advance[Business]" shouldNot compile
  }

  "advance" should "can not decrease service level" in {
    val businessServiceLevel = new ServiceLevelAdvance[Business]
    "businessServiceLevel.advance[Economy]" shouldNot compile
  }

}
