package scala.tools.refactor.scripts

import scala.tools.refactor.tests.utils._
import scala.tools.refactor.printer._

object SourceElements {
  
  def main(args : Array[String]) : Unit = {
    
    import Compiler._
    import compiler._
      
//    val tree = treeFrom("class A(/*1a*/i:/*1b*/Int/*1c*/, /*2a*/s: /*2b*/String/*2c*/) extends AnyRef")
    val tree = treeFrom("class A(i: Int, s: String) extends AnyRef")
    
    
    val transformer = new Transformer {
      override def transform(tree: Tree): Tree = super.transform(tree) match {
        case Template(parents, self, body) => new Template(parents, self, body.reverse).copyAttrs(tree)
        case x => x
      }
    }
    
    val newTree = transformer.transform(tree)
    
    println(Partitioner(compiler, tree) map {
      case se: WhiteSpacePart => "["+se+"]"
      case se: SymbolPart     => "{"+se+"}"
      case se: FlagPart       => "{"+se+"}"
    } mkString " -> ")    
    
    println(Partitioner(compiler, newTree) filter (!_.isWhiteSpace) mkString " -> ")
    
    // why?
    exit(0)
  }
}
