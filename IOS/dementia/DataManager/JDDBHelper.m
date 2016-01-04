//
//  JDDBHelper.m
//  dementia
//
//

#import "JDDBHelper.h"

@implementation JDDBHelper

- (instancetype)init {
    self = [super init];
    if (self) {
        [self createCopyOfDatabaseIfNeeded];
    }
    return self;
}

- (void)createCopyOfDatabaseIfNeeded {
    BOOL existSuccess;
    NSFileManager *fileManager = [NSFileManager defaultManager];
    NSError *errormsg;
    NSArray *dbPaths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *dbDocumentsDirectory = [dbPaths objectAtIndex:0];
    NSString *appDBPath = [dbDocumentsDirectory stringByAppendingPathComponent:DBName];
    
    existSuccess = [fileManager fileExistsAtPath:appDBPath];
    if (existSuccess) {
        return;
    }
    
    NSString *defaultDBPath = [[[NSBundle bundleWithIdentifier:[[NSBundle mainBundle] bundleIdentifier]] resourcePath] stringByAppendingPathComponent:DBName];
    existSuccess = [fileManager copyItemAtPath:defaultDBPath toPath:appDBPath error:&errormsg];
    if (!existSuccess) {
        NSAssert1(0, @"Failed to create writable database file with message '%@'.", [errormsg localizedDescription]);
    }
}

- (FMDatabase *)openDB {
    NSArray  *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *docsPath = [paths objectAtIndex:0];
    NSString *dbPath = [docsPath stringByAppendingPathComponent:DBName];
    
    FMDatabase *db = [FMDatabase databaseWithPath:dbPath];
    return db;
}

- (NSArray *)getDataFromTableWithQuery:(FMResultSet *)query keys:(NSArray *)keys {
    NSMutableArray *resultArray = [NSMutableArray array];
    
    FMResultSet *results;
    
    results = query;
    
    while ([results next]) {
        NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
        
        for (int i=0; i<[keys count]; i++) {
            [dict setObject:results[keys[i]] forKey:keys[i]];
        }
        
        [resultArray addObject:dict];
    }
    
    return resultArray;
}

- (BOOL)updateTableDataWithQuery:(BOOL)query {
    BOOL success;
    success = query;
    
    return success;
}

@end
