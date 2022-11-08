package model

import org.scalatest.funsuite.AnyFunSuite

class FailingTest extends AnyFunSuite :

  test("Failing Test") {
    assert(false)
  }