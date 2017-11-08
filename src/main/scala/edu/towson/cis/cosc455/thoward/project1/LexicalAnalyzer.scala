package edu.towson.cis.cosc455.thoward.project1

trait LexicalAnalyzer {
  //get interface from blackboard
  def addChar() : Unit
  def getChar() : Char
  def getNextToken() : String
  def lookup(candidateString:String) : Boolean
}
