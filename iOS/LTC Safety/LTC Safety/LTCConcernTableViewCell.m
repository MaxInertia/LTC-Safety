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

@end
