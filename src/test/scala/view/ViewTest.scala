package view

import org.junit.jupiter.api.BeforeAll
import org.testfx.util.WaitForAsyncUtils

abstract class ViewTest:

  @BeforeAll
  def setup(): Unit =
    System.setProperty("testfx.robot", "glass")
    System.setProperty("testfx.headless", "true")
    System.setProperty("java.awt.headless", "true")
    System.setProperty("prism.order", "sw")
    System.setProperty("prism.text", "t2k")
    WaitForAsyncUtils.checkAllExceptions = false
    WaitForAsyncUtils.autoCheckException = false
