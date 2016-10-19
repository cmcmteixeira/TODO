package client.router

import client.action.Action
import client.packet.{Packet, PacketAction}
import defaults.{TestApplicationModule, UnitSpec}
import scaldi.Injector
/**
  * Created by carlos on 9/26/16.
  */
class Router$Test extends UnitSpec{
  "The application router" should "handle correctly all package types" in {
    val seq: Iterable[Action] = PacketAction.values.map((action) => {
      val packet = Packet(action = Some(action))
      val ret = (new Router)(packet)
      ret shouldBe a [Action]
      ret
    })
  }
}
