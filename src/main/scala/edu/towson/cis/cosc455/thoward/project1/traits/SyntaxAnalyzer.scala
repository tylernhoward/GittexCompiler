package edu.towson.cis.cosc455.thoward.project1.traits

trait SyntaxAnalyzer {
  //get interface from blackboard
  def gittex() : Unit
  def title() : Unit
  def body() : Unit
  def paragraph() : Unit
  def innerText() : Unit
  def heading() : Unit
  def variableDefine() : Unit
  def variableUse() : Unit
  def bold() : Unit
  def listItem() : Unit
  def innerItem() : Unit
  def link() : Unit
  def image() : Unit
  def newline() : Unit
}
