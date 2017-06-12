package domain;

public class Base implements Command{

	/**
	 * Rotas disponiveis no sistema
	 */
	protected static Graph graph = Graph.create();

	public Base() {
		super();
	}

	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		return false;
	}
}