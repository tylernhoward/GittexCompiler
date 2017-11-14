package edu.towson.cis.cosc455.thoward.project1.traits

trait LexicalAnalyzer {
  //get interface from blackboard
  def addChar() : Unit
  def getChar() : Unit
  def getNextToken() : String
  def lookup(candidateString:String) : Boolean
}
