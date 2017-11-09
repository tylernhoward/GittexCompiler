/*package edu.towson.cis.cosc455.thoward.project1;

public class oldlex {
    package edu.towson.cis.cosc455.thoward.project1


    class MyLexicalAnalyzer extends LexicalAnalyzer{

        private var file : String= ""
        private var currentToken: List[Char] = List()
        private var nextChar:Char = 0
        private var position = 0
        private var validText : List[String] = List()
        private var lexemes : List[String] = List()

        def start(f: String) :Unit = {
            initializeLexemes()
            file = f
            position = 0
            //getChar()
            //getNextToken()
        }

        override def getNextToken(): String = {
            currentToken = List()
            var tokenDiscovered: Boolean = false
            var shouldContinue: Boolean = true

            while(!tokenDiscovered){
                var c = getChar()
                if(Constants.leadSymbols.contains(c) || validText.contains(c.toString())){ //potential start of a token
                    tokenDiscovered = true
                    if(Constants.leadSymbols.contains(c)){
                        if(c == '\\' || c == '!') {
                            addChar()
                            c = getChar()
                            if (c == '\\') {
                                return currentToken.mkString
                            }
                            else {
                                while (shouldContinue) {
                                    if (c == '[') {
                                        addChar()
                                        shouldContinue = false
                                    } else if (isSpace(c)) {
                                        shouldContinue = false
                                    } else {
                                        addChar()
                                        c = getChar()
                                    }
                                }
                            }
                        }
                        else{
                            addChar()
                        }
                        if(lookup(currentToken.mkString)){
                            return currentToken.mkString
                        }
                        else{
                            println(currentToken.mkString + " is not valid")
                        }
                    }
                    else{
                        var textToParse:Boolean = true
                        while(textToParse){
                            if (validText.contains(c.toString())){
                                addChar()
                                c= getChar()
                            }
                            else{
                                textToParse=false
                            }
                        }
                        return currentToken.mkString

                    }

                }
            }
            return " " //This is sloppy

        }

        override def lookup(candidateToken: String): Boolean = {
            lexemes.contains(candidateToken)
        }

        override def getChar(): Char = {
            if (position < file.length()) {
                nextChar = file.charAt(position)
                position = position + 1
            }
            nextChar
        }

        override def addChar(): Unit = {
            currentToken :+= nextChar
        }

        private def isSpace(c: Char):Boolean = {
            Constants.whiteSpace.contains(c.toString())
        }

        private def initializeLexemes() = {
            validText = Constants.letters ::: Constants.numbersEtc
                    lexemes = Constants.tokens

        }

    }
}
*/