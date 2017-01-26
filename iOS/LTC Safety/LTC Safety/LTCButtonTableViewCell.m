//
//  LTCButtonTableViewCell.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-13.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import "LTCButtonTableViewCell.h"

@interface LTCButtonTableViewCell ()
@property (nonatomic, weak) IBOutlet UIButton *button;
@end

@implementation LTCButtonTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    self.contentView.userInteractionEnabled = NO;
    //self.button.layer.cornerRadius = 2.5f;
    //self.button.layer.borderColor = [[UIColor colorWithRed:0xE2/255.0 green:0xE2/255.0 blue:0xE2/255.0 alpha:0xE2/255.0] CGColor];
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    self.button.backgroundColor = [UIColor whiteColor];
    NSLog(@"HERE!");
    // Configure the view for the selected state
}

@end
