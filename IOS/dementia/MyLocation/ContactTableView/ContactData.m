//
//  ContactData.m
//  dementia
//
//

#import "ContactData.h"

@implementation ContactData

- (id)initWithData:(NSDictionary *)dict {
    self = [super init];
    if (self) {
        NSString *documentsPath = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0];
        
        NSString *folderPath = [documentsPath stringByAppendingPathComponent:@"Media/Contact"];
        
        NSString *filePath = [folderPath stringByAppendingPathComponent:dict[@"file_media"]];
        
        self.memberPhoto = [UIImage imageWithContentsOfFile:filePath];
        self.memberName = [NSString stringWithFormat:@"%@(%@)", dict[@"name"], dict[@"relationship"]];
        self.memberPhone = dict[@"phone"];
        self.memberEmail = dict[@"email"];
    }
    return self;
}

@end
