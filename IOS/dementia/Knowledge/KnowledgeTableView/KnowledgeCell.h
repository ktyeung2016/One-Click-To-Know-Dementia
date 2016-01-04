//
//  KnowledgeCell.h
//  dementia
//
//

#import <UIKit/UIKit.h>

#import "KnowledgeData.h"

@interface KnowledgeCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *titleLbl;
@property (weak, nonatomic) IBOutlet UIImageView *btnKnowledgeItem;

- (void)setUpViewWithData:(KnowledgeData *)data;

@end
