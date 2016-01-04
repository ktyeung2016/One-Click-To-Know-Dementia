//
//  JDDBHelper.h
//  dementia
//
//

#import <Foundation/Foundation.h>

#import "FMDatabase.h"
#import "FMResultSet.h"

#define DBName @"DementiaDB"

@interface JDDBHelper : NSObject

- (FMDatabase *)openDB;
- (NSArray *)getDataFromTableWithQuery:(FMResultSet *)query keys:(NSArray *)keys;
- (BOOL)updateTableDataWithQuery:(BOOL)query;

@end
