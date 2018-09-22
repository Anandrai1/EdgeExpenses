package com.edgefintrack.expense.utils;

import com.edgefintrack.expense.EdgeExpensesApp;
import com.edgefintrack.expense.entities.Account;
import com.edgefintrack.expense.entities.Category;
import com.edgefintrack.expense.entities.Expense;
import com.edgefintrack.expense.entities.Reminder;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;


public class RealmManager {

    private static RealmManager ourInstance = new RealmManager();
    private Realm realm;

    public RealmManager() {
        realm = Realm.getInstance(EdgeExpensesApp.getContext());
    }

    public static RealmManager getInstance() {
        return ourInstance;
    }

    public Realm getRealmInstance() {
        return realm;
    }

    public <E extends RealmObject> void update(final E object) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(object);
            }
        });
    }

    public <E extends RealmObject> void update(final Iterable<E> object) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(object);
            }
        });
    }

    public <E extends RealmObject> void save(final E object, final Class<E> clazz) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                checkDuplicateUUID(object, clazz);
                realm.copyToRealmOrUpdate(object);
            }
        });
    }

    public <E extends RealmObject> void delete(final Iterable<E> objects) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (objects == null) {
                    return;
                }
                for (E object : objects) {
                    if (object instanceof Category) {
                        Category category = (Category) object;
                        RealmResults<Expense> expenseList = Expense.getExpensesPerCategory(category);
                        for (int i = expenseList.size() - 1; i >= 0; i--) {
                            expenseList.get(i).removeFromRealm();
                        }
                    }
                    object.removeFromRealm();
                }
            }
        });
    }

    public <E extends RealmObject> void delete(final E object) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (object instanceof Category) {
                    Category category = (Category) object;
                    RealmResults<Expense> expenseList = Expense.getExpensesPerCategory(category);
                    for (int i = expenseList.size() - 1; i >= 0; i--) {
                        expenseList.get(i).removeFromRealm();
                    }
                }
                object.removeFromRealm();
            }
        });
    }

    public <E extends RealmObject> RealmObject findById(Class<E> clazz, String id) {
        return realm.where(clazz).equalTo("id", id).findFirst();
    }

    public <E extends RealmObject> void checkDuplicateUUID(E object, Class<E> clazz) {
        boolean repeated = true;
        while (repeated) {
            String id = UUID.randomUUID().toString();
            RealmObject realmObject = findById(clazz, id);
            if (realmObject == null) {
                if (object instanceof Expense) {
                    ((Expense) object).setId(id);
                } else if (object instanceof Category) {
                    ((Category) object).setId(id);
                }else if (object instanceof Account) {
                    ((Account) object).setId(id);
                } else {
                    ((Reminder) object).setId(id);
                }
                repeated = false;
            }
        }
    }

}
