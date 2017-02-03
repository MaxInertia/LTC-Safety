//
//  LTCValueSelectionController.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-02.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <XLForm/XLForm.h>

@interface LTCValueSelectionController : XLFormViewController <XLFormRowDescriptorViewController>
@property (readonly, nonatomic, assign) BOOL hasOther;
@property (readonly, nonatomic, copy) NSString *fileName;
@property (nonatomic) XLFormRowDescriptor *rowDescriptor;
@end
