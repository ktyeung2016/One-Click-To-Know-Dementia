//
//  GameRankingCell.m
//  dementia
//
//

#import "GameRankingCell.h"

@implementation GameRankingCell

- (void)setUpViewWithData:(GameRankingData *)data {
    NSString *markerName;
    
    switch ([data.ranking integerValue]) {
        case 1:
            [self.rankingView setBackgroundColor:[UIColor colorWithRed:0.949 green:0.784 blue:0.388 alpha:1]];
            markerName = @"Red";
            
            break;
            
        case 2:
            [self.rankingView setBackgroundColor:[UIColor colorWithRed:0.961 green:0.867 blue:0.616 alpha:1]];
            markerName = @"Pink";
            
            break;
            
        case 3:
            [self.rankingView setBackgroundColor:[UIColor colorWithRed:0.976 green:1 blue:0.741 alpha:1]];
            markerName = @"Purple";
            
            break;
            
        default:
            [self.rankingView setBackgroundColor:[UIColor clearColor]];
            markerName = @"Yellow";
            
            break;
    }
    
    [self.markerImgView setImage:[UIImage imageNamed:[NSString stringWithFormat:@"Marker_Game_%@", markerName]]];
    [self.rankingLbl setText:data.ranking];
    [self.scoreLbl setText:data.score];
    [self.dateLbl setText:data.date];
    
    [self setUpTextFontSize];
}

- (void)setUpTextFontSize {
    [self.rankingLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(15.0f)]];
    [self.scoreLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
    [self.dateLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(17.0f)]];
}

@end
