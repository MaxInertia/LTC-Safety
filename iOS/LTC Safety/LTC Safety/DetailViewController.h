//
//  DetailViewController.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-13.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface DetailViewController : UIViewController

@property (strong, nonatomic) NSDate *detailItem;
@property (weak, nonatomic) IBOutlet UILabel *detailDescriptionLabel;

@end

