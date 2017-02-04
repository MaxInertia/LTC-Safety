//
//  LTCConcernTableViewCell.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCConcernTableViewCell.h"

@interface LTCConcernTableViewCell ()
@property (readwrite, nonatomic, strong) LTCConcern *concern;
@property (nonatomic, strong) IBOutlet UILabel *concernNatureLabel;
@property (nonatomic, strong) IBOutlet UILabel *facilityLabel;
@property (nonatomic, strong) IBOutlet UILabel *dateLabel;
@end

@implementation LTCConcernTableViewCell

- (void)configureWithConcern:(LTCConcern *)concern {
    
    NSAssert(concern != nil, @"Attempted to configure a cell with a nil concern.");
    
    self.concernNatureLabel.text = concern.concernNature;
    self.facilityLabel.text = concern.location.facilityName;
    self.dateLabel.text = [NSDateFormatter localizedStringFromDate:concern.submissionDate
                                                         dateStyle:NSDateFormatterMediumStyle
                                                         timeStyle:NSDateFormatterShortStyle];
    
    self.concern = concern;
    
    NSAssert(self.concern != nil, @"Cell finished configuration with a nil concern.");
}

- (void)awakeFromNib {
    [super awakeFromNib];
    
    self.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
}

@end
