import java.io.PrintStream;



public class RegAlloc implements CGConstants {
	private static final int
		R_eq_R_op_R=0,
		S_eq_R_op_R=1,
		R_eq_S_op_R=2,
		S_eq_S_op_R=3,
		R_eq_R_op_S=4,
		S_eq_R_op_S=5,
		R_eq_S_op_S=6,
		S_eq_S_op_S=7;

	
	private RegDesc[] registers;
	
	private PrintStream out;
	
	public RegAlloc(PrintStream out){
		this.out=out;
		registers = new RegDesc[R_MAX];
		for(int i=0; i< R_MAX;i++) registers[i]= new RegDesc(i);
	}
	
	/**
	 * spilling of all registers. Values are dumped in memory if necessary
	 */
	public void spillAll(){
		for(int i=R_RES; i<R_MAX; i++)
			registers[i].spill(i,this.out);
	}
	
	/**
	 * flushAll: spill all registers and clear their descriptors
	 */
	public void flushAll(){
		for(int i=R_RES; i<R_MAX; i++){
			registers[i].spill(i,this.out);
			registers[i].clear();
		}
	}
	/**
	 * clear a descriptor
	 */
	
	public void clearDescriptor(int r){
		registers[r].clear();
	}
	
	/**
	 * clear all descriptors
	 */
	public void clearAll(){
		for(int i=R_RES; i<R_MAX; i++){
			registers[i].clear();
		}
	}
	
	/**
	 * spill a register
	 */
	public void spillOne(int r){
		registers[r].spill(r,this.out);
	}
	

	/**
	 * load a value in a register. 
	 * The register descriptor is updated accordingly  
	 */
	
	public void loadRegisterMIPS( int r, Operand3a op){
		// look if it is in a register
		for (int s=0; s<R_MAX; s++){
			if (registers[s].getMemory() == op){
				out.println(R_INDENT+"move  $"+r+",$"+s);
				setDescriptor(r, op, registers[s].getModif());
				return;
			}
		}
		
		// not in a register. Load appropriately with type
		if (op.isConstInteger()){
			out.println(R_INDENT+"li   $"+r+", "+op.getName3a());
		}
		else{
			if (op.isVarInteger()  ){
				out.println(R_INDENT+"lw  $"+r+ ","+op.getOffset()+"($" + R_STACK + ")" );			
			}
			else if(op.isPointer()){//different from local array				
				out.println(R_INDENT+"lw  $"+r+ ","+op.getOffset()+"($" + R_STACK + ")" );
			}
			else{
				if ( op.isLabel() ){
					// special case of print string					
					out.println(R_INDENT+"lui   $"+ r + ",%hi("+op.getName3a()+")");
					out.println(R_INDENT+"addiu   $"+ r + ",$"+r+",%lo("+op.getName3a()+")");
				}
				else{
					System.out.println("\n loadRegister: unknown case");
				}
			}
		}
		registers[r].set(op,R_UNMODIFIED);
	}
	
	
	public void loadRegisterMIPS(RegDesc r, Operand3a op){
		this.loadRegisterMIPS(r.getRegNumber(),op);
	}
	
	
	
	/**
	 * insert a descriptor entry for the given name
	 */
	public void setDescriptor(int r, Operand3a mem, boolean mod){
		// check if it is already in a register
		for(int re=R_GEN; re<R_MAX; re++)
			if (registers[re].getMemory() == mem) registers[re].clear();
		registers[r].set(mem,mod); 
	}
	
	/**
	 * search for a symbol in a register, return -1 if not found
	 */
	public int search(Operand3a mem){
		int r;
		for(r = R_GEN; r<R_MAX; r++)
			if (registers[r].getMemory() == mem) break;
		if (r<R_MAX) return r;
		else return -1;
	}

	
	/**
	 * getErasable gets a register that will hold an operand and be
	 * overwritten by  the result.
	 */
	public int getErasable( Operand3a op){
		/*  get a register to hold the result of a computation a := b op c
		 * This must initially hold c and will be overwritten with a. If c
		 * is already in a register we use that, spilling it if necessary.
		 * Otherwise we use in order of preference from: 1) an empty register;
		 * 2) an unmodified register; 3) a modified register.
		 * In the last case we spill the content of the register before it
		 * is used. If c is not in the given result register, we load it.
		 * Clearly, we must use only general purpose registers. Note that 
		 * since c may be the same as b, we must update the address and 
		 * register descriptors 
		 */
		int r;
		// already in a register?
		for(r = R_GEN; r<R_MAX; r++)
			if (registers[r].getMemory() == op){
				registers[r].spill(r,this.out);
				return r;
			}
		// find an empty register?
		for(r = R_GEN; r<R_MAX; r++)
			if (registers[r].getMemory() == null){
				loadRegisterMIPS(r,op);
				return r;
			}
		// find an unmodified register?
		for(r = R_GEN; r<R_MAX; r++)
			if (!registers[r].getModif()){
				registers[r].clear();
				loadRegisterMIPS(r,op);
				return r;
			}
		// take a modified register
		registers[R_GEN].spill(R_GEN,this.out);
		registers[R_GEN].clear();
		loadRegisterMIPS(R_GEN,op);
		return R_GEN;
	}

	/**
	 * getAnother get a register to hold the second operand of a computation
	 */
	public int getAnother(Operand3a op, int cr){
		int r;
		// already in a register?
		for(r = R_GEN; r<R_MAX; r++)
			if (registers[r].getMemory() == op &&r!=cr){
				return r;
			}
		// find an empty register?
		for(r = R_GEN; r<R_MAX; r++)
			if (registers[r].getMemory() == null &&r!=cr){
				loadRegisterMIPS(r,op);
				return r;
			}
		// find an unmodified register?
		for(r = R_GEN; r<R_MAX; r++)
			if (!registers[r].getModif()&& (r!=cr)){
				registers[r].clear();
				loadRegisterMIPS(r,op);
				return r;
			}
		// take a modified register different from cr
		for(r = R_GEN; r<R_MAX; r++)
			if (r != cr){
				registers[r].spill(r,this.out);
				registers[r].clear();
				loadRegisterMIPS(r,op);
				return r;
			}
		return -1; // this should never happend
	}
	
	/**
	 * get some register: get a register without associating it with some
	 * place in memory. We use the same policy as above to select it
	 */
	
	public int getSome(int cr){
		int r;
		// find an empty register?
		for(r = R_GEN; r<R_MAX; r++)
			if (registers[r].getMemory() == null){
				return r;
			}
		// find an unmodified register?
		for(r = R_GEN; r<R_MAX; r++)
			if (!registers[r].getModif()&& (r!=cr)){
				registers[r].clear();
				return r;
			}
		// take a modified register (necessarely not cr as this one is newly loaded)
		registers[R_GEN].spill(R_GEN,this.out);
		registers[R_GEN].clear();
		return R_GEN;
	}
	
	public RegDesc getEmptyRegister(){
		int r;
		
		for(r = R_GEN; r<R_MAX; r++)
			if (registers[r].getMemory() == null)
				return registers[r];
		return null;
	}

	//get an empty register different from r1 if it exists
	public RegDesc getEmptyRegister(int r1){
		int r;
		
		for(r = R_GEN; r<R_MAX; r++)
			if (registers[r].getMemory() == null && r!=r1)
				return registers[r];
		return null;
	}
	
	//get an empty register different from r1 and r2 if it exists
	public RegDesc getEmptyRegister(int r1, int r2){
		int r;
		
		for(r = R_GEN; r<R_MAX; r++)
			if (registers[r].getMemory() == null && r!=r1 && r!=r2)
				return registers[r];
		return null;
	}

	
	public RegDesc getNotModifiedRegister(){
		int r;
		
		for(r = R_GEN; r<R_MAX; r++)
			if (registers[r].getModif() == false)
				return registers[r];
		return null;
	}
	

	//get a not modified register different from r1 if it exists
	public RegDesc getNotModifiedRegister(int r1){
		int r;
		
		for(r = R_GEN; r<R_MAX; r++)
			if (registers[r].getModif() == false && r!=r1)
				return registers[r];
		return null;
	}
	
	//get a not modified register different from r1 and r2 if it exists
	public RegDesc getNotModifiedRegister(int r1,int r2){
		int r;
		
		for(r = R_GEN; r<R_MAX; r++)
			if (registers[r].getModif() == false && r!=r1 && r!=r2)
				return registers[r];
		return null;
	}
	
	
	public RegDesc isInRegister(Operand3a op){
		int r = this.search(op);
		if(r==-1)
			return null;
		else
			return	registers[r];
	}

	
	public RegDesc allocateOneRegister(){
		RegDesc reg;
		
		reg = this.getEmptyRegister();
		if(reg != null)
			return reg;
		reg = this.getNotModifiedRegister();
		if(reg != null)
			return reg;
		reg = this.registers[R_GEN];
		this.spillOne(R_GEN);
		return reg;
	}
	
	
	//allocate a register different from r1
	public RegDesc allocateOneRegister(int r1){
		RegDesc reg;
		
		reg = this.getEmptyRegister(r1);
		if(reg != null ){
			return reg;
		}
		
		reg = this.getNotModifiedRegister(r1);
		if(reg != null ){
			return reg;
		}
		
		for(int r=R_GEN;r<R_MAX; r++){
			if(r!=r1 ){
				reg = this.registers[r];
				this.spillOne(r);
				return reg;
			}
		}
		return null;
	}
	
	//allocate a register different from r1 and r2
	public RegDesc allocateOneRegister(int r1,int r2){
		RegDesc reg;
		
		reg = this.getEmptyRegister(r1,r2);
		if(reg != null )
			return reg;
		
		reg = this.getNotModifiedRegister(r1,r2);
		if(reg != null )
			return reg;
		
		for(int r=R_GEN;r<R_MAX; r++){
			if(r!=r1 && r!=r2){
				reg = this.registers[r];
				this.spillOne(r);
				return reg;
			}
		}
		return null;
	}
	
	
	/**
	 * Allocate three registers for a 3-addresses opcode
	 * 
	 * @param dest 
	 * @param op1
	 * @param op2
	 * @return
	 */
	public Triplet<RegDesc> allocateRegisters(Operand3a dest, Operand3a op1, Operand3a op2){
		// Each operand can be in two states:
		// - Already bound to a register (nothing to be done)
		// - Not bound to a register (we need to find one).
		// 2^3 cases have to be considered.
	
		RegDesc destReg = this.isInRegister(dest);
		RegDesc op1Reg = this.isInRegister(op1);
		RegDesc op2Reg = this.isInRegister(op2);
		int destInRegister = (destReg != null)?0:1 ;
		int op1InRegister = (op1Reg != null)?0:2 ;
		int op2InRegister = (op2Reg != null)?0:4 ;
		int situation = destInRegister + op1InRegister + op2InRegister;
		
		switch(situation){
		case R_eq_R_op_R:
			// All three operands are already associated to registers
			break;
		case S_eq_R_op_R:
			if(!op1Reg.getModif()){
				destReg = op1Reg;
				break;
			}
			if(!op2Reg.getModif()){
				destReg = op2Reg;
				break;
			}
			destReg = this.allocateOneRegister(op1Reg.getRegNumber(),op2Reg.getRegNumber());
			break;
		case R_eq_R_op_S:
			op2Reg = destReg;
			this.loadRegisterMIPS(op2Reg,op2);
			break;
		case R_eq_S_op_R:
			op1Reg = destReg;
			this.loadRegisterMIPS(op1Reg,op1);
			break;
		case S_eq_R_op_S:
			op2Reg = this.allocateOneRegister(op1Reg.getRegNumber());
			this.loadRegisterMIPS(op2Reg, op2);
			destReg = op2Reg;
			break;
		case S_eq_S_op_R:
			op1Reg = this.allocateOneRegister(op2Reg.getRegNumber());
			this.loadRegisterMIPS(op1Reg, op1);
			destReg = op1Reg;
			break;
		case R_eq_S_op_S:
			op1Reg = destReg;
			this.loadRegisterMIPS(op1Reg, op1);
			op2Reg = this.allocateOneRegister(op1Reg.getRegNumber());
			this.loadRegisterMIPS(op2Reg, op2);
			break;
		case S_eq_S_op_S:
			destReg = this.allocateOneRegister();
			op2Reg=destReg;
			op1Reg = this.allocateOneRegister(destReg.getRegNumber());
			this.loadRegisterMIPS(op1Reg, op1);
			this.loadRegisterMIPS(destReg, op2);
			
			break;
		}
		
		// In all cases the destination register now contains a value associated
		// with dest operand, and it has been modified.
		destReg.set(dest, true);
		
		return new Triplet<RegDesc>(destReg, op1Reg, op2Reg);
	}
	
}
