package edu.towson.cis.cosc455.thoward.project1

trait SyntaxAnalyzer {
  //get interface from blackboard
  def gittex() : Unit
  def title() : Unit
  def body() : Unit
  def paragraph() : Unit
  def heading() : Unit
  def variableDefine() : Unit
  def variableUse() : Unit
  def bold() : Unit
  def listItem() : Unit
  def link() : Unit
  def image() : Unit
  def newline() : Unit
}
