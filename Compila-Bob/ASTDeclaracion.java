/* Generated By:JJTree: Do not edit this line. ASTDeclaracion.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY= */
public class ASTDeclaracion extends SimpleNode {
  private String name;
  public ASTDeclaracion(int id) {
    super(id);
  }

  public String toString() {
      return "Declaracion de variable: " + this.name;
  }

  public void setName(String n) {
    this.name = n;
  }

}
/* JavaCC - OriginalChecksum=109bafe49f780d9dc304ed80d007a324 (do not edit this line) */
