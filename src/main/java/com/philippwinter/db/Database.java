package com.philippwinter.db;

import com.philippwinter.db.model.Column;
import com.philippwinter.db.model.ForeignKeyConstraints;
import com.philippwinter.db.model.Table;

import java.util.List;

import static com.philippwinter.db.model.Column.DataType.*;
import static java.util.Arrays.asList;

/**
 * All copyright by Philipp Winter, 2017.
 */
public class Database {

    private static Database instance;

    private Table orders = new Table("UNI_ORDERS").setGenerateRows(1500000);
    private Table customers = new Table("UNI_CUSTOMERS").setGenerateRows(1200);
    private Table recipes = new Table("UNI_RECIPES").setGenerateRows(3400);
    private Table employees = new Table("UNI_EMPLOYEES").setGenerateRows(950);
    private Table departments = new Table("UNI_DEPARTMENTS").setGenerateRows(35);
    private Table addresses = new Table("UNI_ADDRESSES2").setGenerateRows(2000);
    private Table products = new Table("UNI_PRODUCT").setGenerateRows(2500);
    private Table ingredients = new Table("UNI_INGREDIENTS").setGenerateRows(666);
    private Table productHasIngredients = new Table("UNI_PRODUCT_HAS_INGREDIENTS").setGenerateRows(1200);
    private Table ingredientPrice = new Table("UNI_INGREDIENT_PRICE").setGenerateRows(30000);
    private Table sales = new Table("UNI_SALES").setGenerateRows(1200000);
    private Table productGroups = new Table("UNI_PRODUCT_GROUPS").setGenerateRows(75);
    private Table commissions = new Table("UNI_COMMISSIONS").setGenerateRows(530000);
    private Table commissionCategories = new Table("UNI_COMMISSION_CATEGORIES").setGenerateRows(20);

    private List<Table> tables = asList(
            orders, customers, recipes, employees, departments, addresses, products, ingredients, productGroups, commissions, commissionCategories, sales, productHasIngredients, ingredientPrice
    );

    public static Database getInstance() {
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private Database() {
        Column idColumn = new Column("ID", NUMBER, false, true, false, true, null);

        Column orderId = new Column(idColumn);
        Column productId = new Column(idColumn);
        Column customerId = new Column(idColumn);
        Column recipeId = new Column(idColumn);
        Column employeeId = new Column(idColumn);
        Column addressId = new Column(idColumn);
        Column ingredientId = new Column(idColumn);
        Column productGroupId = new Column(idColumn);
        Column commissionCategoryId = new Column(idColumn);
        Column departmentId = new Column(idColumn);
        Column salesId = new Column(idColumn);

        orders.setColumns(asList(
                orderId,
                fk("PRODUCT_ID", productId, false),
                fk("CUSTOMER_ID", customerId, false),
                number("QUANTITY", false),
                date("DELIVER_BY", false),
                date("SUBMITTED", true))
        );
        customers.setColumns(asList(
                customerId,
                string("NAME", false),
                fk("ADDRESS_ID", addressId, false),
                fk("CONTACT_EMPLOYEE_ID", employeeId, false)
        ));
        recipes.setColumns(asList(
                recipeId,
                date("CREATED_DATE", false),
                date("DELETED_DATE", true),
                fk("PRODUCT_ID", productId, false)
        ));
        employees.setColumns(asList(
                employeeId,
                string("FIRST_NAME", false),
                string("LAST_NAME", false),
                string("EMAIL", false),
                fk("DEPARTMENT_ID", departmentId, false),
                fk("ADDRESS_ID", addressId, false)
        ));
        departments.setColumns(asList(
                departmentId,
                string("NAME", false),
                fk("LEADER_ID", employeeId, false)
        ));
        addresses.setColumns(asList(
                addressId,
                string("STREET", false),
                string("CITY", false),
                string("ZIP", false),
                string("COUNTRY", false)
        ));
        products.setColumns(asList(
                productId,
                string("NAME", false),
                text("DESCRIPTION", true),
                fk("CREATOR_ID", employeeId, false),
                fk("PRODUCT_GROUP_ID", productGroupId, false)
        ));
        ingredients.setColumns(asList(
                ingredientId,
                string("NAME", false)
        ));
        productHasIngredients.setColumns(asList(
                fk("PRODUCT_ID", productId, false).setPartOfKey(true),
                fk("INGREDIENT_ID", ingredientId, false).setPartOfKey(true),
                floating("WEIGHT", false),
                text("COMMENT", true)
        ));
        ingredientPrice.setColumns(asList(
                fk("PRODUCT_ID", productId, false).setPartOfKey(true),
                new Column("DATE", DATE, false, true, false, false, null),
                floating("REFERENCE_WEIGHT", false),
                floating("PRICE", false)
        ));
        sales.setColumns(asList(
                salesId,
                fk("ORDER_ID", orderId, false),
                date("DATE", false),
                floating("PRICE", false)
        ));
        productGroups.setColumns(asList(
                productGroupId,
                string("NAME", false),
                fk("PARENT", productGroupId, true)
        ));
        commissions.setColumns(asList(
                fk("ORDER_ID", orderId, false).setPartOfKey(true),
                fk("EMPLOYEE_ID", employeeId, false).setPartOfKey(true),
                fk("COMMISSION_CATEGORY_ID", commissionCategoryId, false)
        ));
        commissionCategories.setColumns(asList(
                commissionCategoryId,
                string("NAME", false),
                floating("REWARD_PCT", false)
        ));
    }

    public static String generateForeignKeyConstraintName(Column column) {
        return "fk_" +
                stringOfMaxLength(12, column.getTable().getName().toLowerCase()) +
                "_" +
                stringOfMaxLength(12, column.getName().toLowerCase());
    }

    public static String generateForeignKeyIndexName(Column column) {
        return "fk_index_" +
                stringOfMaxLength(9, column.getTable().getName().toLowerCase()) +
                "_" +
                stringOfMaxLength(9, column.getName().toLowerCase());
    }

    public static String generatePrimaryKeyIndexName(Column column) {
        return column.getTable().getName() + "_PK";
    }

    private static String stringOfMaxLength(int maxLength, String s) {
        if (s.length() <= maxLength) {
            return s;
        }

        return s.substring(0, maxLength);
    }

    private static Column date(String name, boolean nullable) {
        return new Column(name, DATE, nullable);
    }

    private static Column fk(String name, Column referenced, boolean nullable) {
        return new Column(name, NUMBER, nullable, false, false, false, new ForeignKeyConstraints(referenced));
    }

    private static Column number(String name, boolean nullable) {
        return new Column(name, NUMBER, nullable);
    }

    private static Column string(String name, boolean nullable) {
        return new Column(name, STRING, nullable);
    }

    private static Column text(String name, boolean nullable) {
        return new Column(name, TEXT, nullable);
    }

    private static Column floating(String name, boolean nullable) {
        return new Column(name, FLOAT, nullable);
    }

    public List<Table> getTables() {
        return tables;
    }
}
