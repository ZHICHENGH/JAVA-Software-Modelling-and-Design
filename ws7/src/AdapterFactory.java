public class AdapterFactory {
    private static AdapterFactory _instance;

    private AdapterFactory(){}
    public static AdapterFactory instance() {
        if(_instance == null) _instance = new AdapterFactory();
        return _instance;
    }

    public SearchAdapter getAdapter(int index) {
        switch (index) {
            case 0:
                return new AllAdapter();
            case 1:
                return new JestarAdapter();
            case 2:
                return new QantasAdapter();
            case 3:
                return new VirginAdapter();
            default:
                return null;
        }
    }

}
