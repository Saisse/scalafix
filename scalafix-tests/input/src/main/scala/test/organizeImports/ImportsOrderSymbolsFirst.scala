/*
rules = [OrganizeImports]
OrganizeImports.removeUnused = false
OrganizeImports {
  groupedImports = Keep
  importSelectorsOrder = Keep
  importsOrder = SymbolsFirst
  targetDialect = Auto
}
 */
package test.organizeImports

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent._
import scala.concurrent.duration
import scala.concurrent.{Promise, Future}

import test.organizeImports.QuotedIdent.`a.b`.`{ d }`.e
import test.organizeImports.QuotedIdent.`a.b`.`{ d }`.{  e => E  }
import test.organizeImports.QuotedIdent.`a.b`
import test.organizeImports.QuotedIdent.{`a.b` => ab}
import test.organizeImports.QuotedIdent.`a.b`.{c => _, _}
import test.organizeImports.QuotedIdent._

object ImportsOrderSymbolsFirst
