package practice.budget_tracker.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    public List<Expense> getExpensesByCategoryIgnoreCase(String category);

}

