# RoomKit

A lightweight helper library that makes writing to a **Room SQL** database on Android simpler and less repetitive.

Room already does the heavy lifting over raw SQLite, but every project ends up rewriting the same boilerplate: basic DAO operations, the database builder, and type converters for common types like `Date`, `UUID`, and `List`. RoomKit bundles those so you don't have to.

---

## Features

- **`BaseDao<T>`** - a generic DAO that gives every entity `insert`, `insertAll`, `update`, and `delete` for free. Just extend it.
- **`RoomKit.build()`** - build a Room database in one line instead of the verbose builder.
- **`RoomKitConverters`** - ready-made `TypeConverter`s for `Date`, `UUID`, and `List<String>` (lists stored as JSON), so these types "just work" in your entities.

---

## Requirements

- `minSdk` 24+
- Kotlin
- Room 2.6.1+

---

## Installation (via JitPack)

Add JitPack to your **root** `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Then add the dependency in your **app/module** `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.Ariel-Yitzhaki:room-kit:1.0.0")
}
```

Replace `1.0.0` with the latest release tag.

---

## Usage

### 1. Define an entity with rich types

Thanks to the bundled converters, you can use `Date`, `UUID`, and `List<String>` directly:

```kotlin
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val createdAt: Date,
    val roles: List<String>
)
```

### 2. Create a DAO by extending `BaseDao`

```kotlin
@Dao
interface UserDao : BaseDao<User> {
    // insert, insertAll, update, delete are inherited automatically

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<User>
}
```

### 3. Register the converters on your database

```kotlin
@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(RoomKitConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
```

### 4. Build the database in one line

```kotlin
val db = RoomKit.build<AppDatabase>(context, "app.db")
```

### 5. Use it

```kotlin
db.userDao().insert(
    User(name = "Alice", createdAt = Date(), roles = listOf("admin", "editor"))
)

val users = db.userDao().getAll()
// users[0].roles -> ["admin", "editor"]  (round-tripped through JSON)
```

---

## Supported converters

| Type           | Stored as          |
|----------------|--------------------|
| `Date`         | `Long` (timestamp) |
| `UUID`         | `String`           |
| `List<String>` | JSON `String`      |

---

## License

MIT License. See [LICENSE](LICENSE) for details.
