package atm.service.impl;

import atm.service.MiniStatementService;
import java.util.ArrayList;
import java.util.List;

public class MiniStatementServiceImpl implements MiniStatementService {
    private static MiniStatementServiceImpl instance;

    private final List<String> miniStatement = new ArrayList<>();

    private MiniStatementServiceImpl() {}

    public static synchronized MiniStatementServiceImpl getInstance() {
        if (instance == null) {
            instance = new MiniStatementServiceImpl();
        }
        return instance;
    }

    @Override
    public void viewMiniStatement() {
        if (miniStatement.isEmpty()) {
            System.out.println("No transactions available.");
        } else {
            System.out.println("Mini Statement:");
            for (String entry : miniStatement) {
                System.out.println(entry);
            }
        }
    }

    @Override
    public void addEntry(String entry) {
        miniStatement.add(entry);
    }
}
